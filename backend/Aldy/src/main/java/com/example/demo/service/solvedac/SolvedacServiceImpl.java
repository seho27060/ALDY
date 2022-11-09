package com.example.demo.service.solvedac;

import com.example.demo.domain.dto.solvedac.*;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.solvedac.response.ProblemRecommendationResponseDto;
import com.example.demo.domain.dto.solvedac.response.SolvedacMemberResponseDto;
import com.example.demo.domain.dto.solvedac.ProblemWithTagsVo;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.service.crawling.CrawlingServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SolvedacServiceImpl implements SolvedacService {

    private final WebClient webClient;
    private final CrawlingServiceImpl crawlingService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RedisTemplate<String,Object> problemRedisTemplate;

    @Override
    public SolvedacSearchProblemDto filter(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList, int page) {

        String query = makeQuery(algoList, tierList, baekjoonIdList);

        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/search/problem")
                                .queryParam("query", query)
                                .queryParam("page", page)
                                .queryParam("sort", "solved")
                                .queryParam("direction", "desc")
                                .build())
                .acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SolvedacSearchProblemDto.class)
                .blockOptional().get();

    }

    private String makeQuery(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList) {

        StringBuilder query = new StringBuilder();

        // "(tier:g1|tier:g2|tier:g3)&"
        if(tierList != null) {
            query.append("(");
            tierList.forEach(t -> query.append("tier:").append(t).append("|"));
            query.deleteCharAt(query.length() - 1);
            query.append(")&");
        }
        // "(tag:bfs|tag:dfs)&"
        if(algoList != null) {
            query.append("(");
            algoList.forEach(a -> query.append("tag:").append(a).append("|"));
            query.deleteCharAt(query.length() - 1);
            query.append(")&");
        }
        // "!(solved_by:seho27060|solved_by:min61037)"
        if(baekjoonIdList != null) {
            query.append("!(");
            baekjoonIdList.forEach(b -> query.append("solved_by:").append(b).append("|"));
            query.deleteCharAt(query.length() - 1);
            query.append(")&");
        }

        if(query.length() > 0) {
            query.deleteCharAt(query.length() - 1);
        }

        return query.toString();
    }

    public Optional<SolvedacMemberResponseDto> solvedacMemberFindAPI(String baekjoonId){
        Optional<SolvedacMemberResponseDto> mono;
        mono = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/user/show")
                                .queryParam("handle", baekjoonId)
                                .build())
                .acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status ->
                                status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse ->
                                clientResponse
                                        .bodyToMono(String.class)
                                        .map(body -> new CustomException(ErrorCode.NOT_EXIST_MEMBER)))
                .bodyToMono(SolvedacMemberResponseDto.class)
                .flux()
                .toStream()
                .findFirst();
        return mono;
    }

    @Override
    public ProblemWithTagDisplayNamesVo recommendProblem(HttpServletRequest request) throws IOException {
        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member loginMember = memberRepository.findByBaekjoonId(baekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        HashOperations hashOperations = problemRedisTemplate.opsForHash();

        if(!hashOperations.hasKey(baekjoonId,"items")){
            System.out.println("PUT REDIS");
            List<ProblemWithTagsVo> solvedProblemList;
            solvedProblemList = SolvedacProblemLookup(baekjoonId);

            HashMap<String,Long> tagMap = new HashMap<>();

            // 가져온 문제들의 알고리즘 분류 카운트
            for(ProblemWithTagsVo solvedProblem : solvedProblemList){
                for(ProblemTagsDto problemTagsDto: solvedProblem.getTags()){
                    String tagDisplayName = problemTagsDto.getKey();
                    tagMap.put(tagDisplayName,tagMap.getOrDefault(tagDisplayName,0L)+1);
                }
            }

            // 카운트 상위 최대 3개의 알고리즘 분류 가져오기
            List<String> keyset = new ArrayList<>(tagMap.keySet());
            keyset.sort(((o1, o2) -> tagMap.get(o2).compareTo(tagMap.get(o1))));

            StringBuilder algos = new StringBuilder();
            for (int idx = 0; idx < Math.min(keyset.size(),5);idx++){
                algos.append(keyset.get(idx));
                algos.append("|");
            }
            algos.deleteCharAt(algos.length()-1);

            // 쿼리 생성
            // 옵션 주고 문제 api
            // 최대 50개중 랜덤으로 반환
            String query = String.format("(~solved_by:%s)&(tag:%s)&(tier:%d..%d)&(lang:ko)&(solveable:true)",
                    loginMember.getBaekjoonId(),
                    algos,
                    Math.max(0,loginMember.getTier()-1),
                    Math.min(31,loginMember.getTier()+1));

            // 랜덤한 문제를 뽑고.해당 문제의 알고리즘 중 랜덤하게 출력하는 과정.
            SolvedacSearchProblemDto solvedacSearchProblemDto = SolvedacSearchProblemForRecommendation(query)
                    .orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
            hashOperations.put(baekjoonId,"maxCount",Math.min(solvedacSearchProblemDto.getCount(),50));
            hashOperations.put(baekjoonId,"items", solvedacSearchProblemDto.getItems());
            hashOperations.put(baekjoonId,"count",0);
        } else{
            System.out.println("GET AND DELETE REDIS");
            System.out.println(hashOperations.get(baekjoonId,"items"));
            System.out.println(hashOperations.get(baekjoonId,"items").getClass());
            System.out.printf("max :%d count :%d",hashOperations.get(baekjoonId,"maxCount"),hashOperations.get(baekjoonId,"count"));

            List<ProblemWithTagDisplayNamesVo> whatisList = (List<ProblemWithTagDisplayNamesVo>) hashOperations.get(baekjoonId,"items");
            System.out.println(whatisList.get(0));
            ObjectMapper objectMapper = new ObjectMapper();
            whatisList = objectMapper.convertValue(hashOperations.get(baekjoonId, "items"), new TypeReference<List<ProblemWithTagDisplayNamesVo>>() {});
            // 저장해서, 가져오고, 원하는 객체로 변환해주고..
            // 랜덤한 문제 뽑기
            // 뽑은 번호 리스트를 갖고 있기
            // 랜덤 번호 뽑아서 리스트에 없으면 그대로 출력 + 랜덤 번호 리스트에 추가 후 캐시 값 수정
            // 랜덤 번호 리스트 길이가 캐시의 maxLen 이상이면 캐시 삭제
            // 캐시가 삭제되면 다음 요청때 api 새로 요청해서 갱신함.
            System.out.println(whatisList.get(0));
            System.out.println(whatisList.get(0).getClass());
            hashOperations.delete(baekjoonId,"maxCount","items","count");
        }
        // 문제 번호 20개로 문제 20개 불러오기
        List<ProblemWithTagsVo> solvedProblemList;
        solvedProblemList = SolvedacProblemLookup(baekjoonId);

        HashMap<String,Long> tagMap = new HashMap<>();

        // 가져온 문제들의 알고리즘 분류 카운트
        for(ProblemWithTagsVo solvedProblem : solvedProblemList){
            for(ProblemTagsDto problemTagsDto: solvedProblem.getTags()){
                String tagDisplayName = problemTagsDto.getKey();
                tagMap.put(tagDisplayName,tagMap.getOrDefault(tagDisplayName,0L)+1);
            }
        }

        // 카운트 상위 최대 3개의 알고리즘 분류 가져오기
        List<String> keyset = new ArrayList<>(tagMap.keySet());
        keyset.sort(((o1, o2) -> tagMap.get(o2).compareTo(tagMap.get(o1))));

        StringBuilder algos = new StringBuilder();
        for (int idx = 0; idx < Math.min(keyset.size(),5);idx++){
            algos.append(keyset.get(idx));
            algos.append("|");
        }
        algos.deleteCharAt(algos.length()-1);

        // 쿼리 생성
        // 옵션 주고 문제 api
        // 최대 50개중 랜덤으로 반환
        String query = String.format("(~solved_by:%s)&(tag:%s)&(tier:%d..%d)&(lang:ko)&(solveable:true)",
                loginMember.getBaekjoonId(),
                algos,
                Math.max(0,loginMember.getTier()-1),
                Math.min(31,loginMember.getTier()+1));

        // 랜덤한 문제를 뽑고.해당 문제의 알고리즘 중 랜덤하게 출력하는 과정.
        SolvedacSearchProblemDto solvedacSearchProblemDto = SolvedacSearchProblemForRecommendation(query)
                .orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        ProblemWithTagDisplayNamesVo randomProblem = solvedacSearchProblemDto
                .getItems().get((int) (Math.random()*Math.min(solvedacSearchProblemDto.getCount(),50)));

        return randomProblem;
    }

    private List<ProblemWithTagsVo> SolvedacProblemLookup(String  baekjoonId) throws IOException {

        // 로그인 사용자의 최근 푼 문제 번호 20개 가져오기

        ArrayList<Integer> latestSolvedProblemIdList = crawlingService.getRecentProblems(baekjoonId);


        StringBuilder problemsIds = new StringBuilder();
        for(int latestSolvedProblemId : latestSolvedProblemIdList){
            problemsIds.append(latestSolvedProblemId).append(",");
        }
        problemsIds.deleteCharAt(problemsIds.length()-1);

        // 문제 번호 20개로 문제 20개 불러오기
        List<ProblemWithTagsVo> solvedProblemList;

        solvedProblemList = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/problem/lookup")
                                .queryParam("problemIds", problemsIds)
                                .build())
                .acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status ->
                                status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse ->
                                clientResponse
                                        .bodyToMono(String.class)
                                        .map(body -> new CustomException(ErrorCode.NOT_EXIST_MEMBER)))
                .bodyToFlux(ProblemWithTagsVo.class)
                .toStream()
                .collect(Collectors.toList());

        return solvedProblemList;
    }

    private Optional<SolvedacSearchProblemDto> SolvedacSearchProblemForRecommendation(String query){
        Optional<SolvedacSearchProblemDto> solvedacSearchProblemDto;
        solvedacSearchProblemDto = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/search/problem")
                                .queryParam("query", query)
                                .queryParam("page", 1)
                                .queryParam("sort", "solved")
                                .queryParam("direction", "desc")
                                .build())
                .acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status ->
                                status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse ->
                                clientResponse
                                        .bodyToMono(String.class)
                                        .map(body -> new CustomException(ErrorCode.SOLVEDAC_ERROR)))
                .bodyToMono(SolvedacSearchProblemDto.class)
                .flux()
                .toStream()
                .findFirst();
        return solvedacSearchProblemDto;
    }

}
