package com.example.demo.service.solvedac;

import com.example.demo.domain.dto.solvedac.*;

import com.example.demo.config.jwt.JwtTokenProvider;
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
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Override
    public SolvedacSearchProblemDto search(String keyword) {

        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/search/problem")
                                .queryParam("query", keyword)
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

        query.append("(lang:ko)");

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

        HashOperations<String, Object, Object> hashOperations = problemRedisTemplate.opsForHash();

        int randomIndex = 0;
        List<ProblemWithTagDisplayNamesVo> problemWithTagDisplayNamesVoList = new ArrayList<>();

        if(!hashOperations.hasKey(baekjoonId,"items")){
            renewRecommendProblemList(request);
            System.out.println("PUT REDIS");

            String algorithmQuery = String.valueOf(GetAlgorithmQuery(baekjoonId));

            String query = String.format(
                    "(~solved_by:%s)&(tag:%s)&(tier:%d..%d)&(lang:ko)&(solveable:true)",
                    loginMember.getBaekjoonId(),
                    algorithmQuery,
                    Math.max(0,loginMember.getTier()-1),
                    Math.min(31,loginMember.getTier()+1));

            // ????????? ????????? ??????.?????? ????????? ???????????? ??? ???????????? ???????????? ??????.
            SolvedacSearchProblemDto solvedacSearchProblemDto = SearchProblemWithQuery(query)
                    .orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

            int maxCount = (int) Math.min(solvedacSearchProblemDto.getCount(),50);
            List<Integer> indexList = IntStream.iterate(0, i -> i < maxCount, i -> i + 1)
                    .boxed()
                    .collect(Collectors.toList());
            Collections.shuffle(indexList);

            hashOperations.put(baekjoonId,"indexList",indexList);
            hashOperations.put(baekjoonId,"items", solvedacSearchProblemDto.getItems());
            hashOperations.put(baekjoonId,"count",1);
            hashOperations.getOperations().expire(baekjoonId,7, TimeUnit.DAYS);

            problemWithTagDisplayNamesVoList = solvedacSearchProblemDto.getItems();
            randomIndex = indexList.get(0);
        } else{
            System.out.println("GET REDIS");
            Map<Object, Object> entries = hashOperations.entries(baekjoonId);
            // ???????????? ????????? ??? ???????????? objectmapper??? ???????????? ?????? ???????????? ??????
            ObjectMapper objectMapper = new ObjectMapper();
            problemWithTagDisplayNamesVoList = objectMapper.convertValue(entries.get("items"), new TypeReference<>() {});

            // ????????????, ????????????, ????????? ????????? ???????????????..
            // ????????? ?????? ??????
            // ?????? ?????? ???????????? ?????? ??????
            // ?????? ?????? ????????? ???????????? ????????? ????????? ?????? + ?????? ?????? ???????????? ?????? ??? ?????? ??? ??????
            // ?????? ?????? ????????? ????????? ????????? maxLen ???????????? ?????? ??????
            // ????????? ???????????? ?????? ????????? api ?????? ???????????? ?????????.

            List<Integer> indexList = (List<Integer>) entries.get("indexList");
            int count = (int) entries.get("count");
            randomIndex = indexList.get(count);

            count = count + 1;
            System.out.printf("???????????? %d ?????? ???",count);
            if(count >= indexList.size()){
                hashOperations.delete(baekjoonId,"items","count","indexList");
                System.out.println("DELETE REDIS");
            }else{
                hashOperations.put(baekjoonId,"count",count);
            }
        }

        ProblemWithTagDisplayNamesVo randomProblem = problemWithTagDisplayNamesVoList.get(randomIndex);

        return randomProblem;
    }

    private List<ProblemWithTagsVo> SolvedacProblemLookup(String  baekjoonId) throws IOException {

        // ????????? ???????????? ?????? ??? ?????? ?????? 20??? ????????????

        ArrayList<Integer> latestSolvedProblemIdList = crawlingService.getRecentProblems(baekjoonId);


        StringBuilder problemsIds = new StringBuilder();
        for(int latestSolvedProblemId : latestSolvedProblemIdList){
            problemsIds.append(latestSolvedProblemId).append(",");
        }
        problemsIds.deleteCharAt(problemsIds.length()-1);

        // ?????? ?????? 20?????? ?????? 20??? ????????????
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

    private Optional<SolvedacSearchProblemDto> SearchProblemWithQuery(String query){
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

    private StringBuilder GetAlgorithmQuery(String baekjoonId) throws IOException {
        List<ProblemWithTagsVo> solvedProblemList;
        solvedProblemList = SolvedacProblemLookup(baekjoonId);

        HashMap<String,Long> tagMap = new HashMap<>();

        // ????????? ???????????? ???????????? ?????? ?????????
        for(ProblemWithTagsVo solvedProblem : solvedProblemList){
            for(ProblemTagsDto problemTagsDto: solvedProblem.getTags()){
                String tagDisplayName = problemTagsDto.getKey();
                tagMap.put(tagDisplayName,tagMap.getOrDefault(tagDisplayName,0L)+1);
            }
        }

        // ????????? ?????? ?????? 3?????? ???????????? ?????? ????????????
        List<String> keyset = new ArrayList<>(tagMap.keySet());
        keyset.sort(((o1, o2) -> tagMap.get(o2).compareTo(tagMap.get(o1))));

        StringBuilder algos = new StringBuilder();
        for (int idx = 0; idx < Math.min(keyset.size(),5);idx++){
            algos.append(keyset.get(idx));
            algos.append("|");
        }
        algos.deleteCharAt(algos.length()-1);
        return algos;
    }

    public void renewRecommendProblemList(HttpServletRequest request) throws IOException {
        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member loginMember = memberRepository.findByBaekjoonId(baekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        HashOperations<String, Object, Object> hashOperations = problemRedisTemplate.opsForHash();
        hashOperations.delete(baekjoonId,"items","count","indexList");
        System.out.println("PUT REDIS");

        String algorithmQuery = String.valueOf(GetAlgorithmQuery(baekjoonId));

        String query = String.format(
                "(~solved_by:%s)&(tag:%s)&(tier:%d..%d)&(lang:ko)&(solveable:true)",
                loginMember.getBaekjoonId(),
                algorithmQuery,
                Math.max(0,loginMember.getTier()-1),
                Math.min(31,loginMember.getTier()+1));

        // ????????? ????????? ??????.?????? ????????? ???????????? ??? ???????????? ???????????? ??????.
        SolvedacSearchProblemDto solvedacSearchProblemDto = SearchProblemWithQuery(query)
                .orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        int maxCount = (int) Math.min(solvedacSearchProblemDto.getCount(),50);
        List<Integer> indexList = IntStream.iterate(0, i -> i < maxCount, i -> i + 1)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(indexList);

        hashOperations.put(baekjoonId,"indexList",indexList);
        hashOperations.put(baekjoonId,"items", solvedacSearchProblemDto.getItems());
        hashOperations.put(baekjoonId,"count",1);
        hashOperations.getOperations().expire(baekjoonId,7, TimeUnit.DAYS);
    }
}
