package com.example.demo.service;


import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.solvedac.ProblemDto;
import com.example.demo.domain.dto.solvedac.response.SolvedacMemberResponseDto;
import com.example.demo.domain.dto.ProblemFilterDto;
import com.example.demo.domain.dto.solvedac.SolvedacSearchProblemDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.Charset;
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
    @Override
    public ProblemFilterDto filter(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList, int page) {

        String query = makeQuery(algoList, tierList, baekjoonIdList);

        ProblemFilterDto problemFilterDto = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/search/problem")
                                .queryParam("query", query)
                                .queryParam("page", page)
                                .queryParam("sort", "solved")
                                .queryParam("direction", "desc")
                                .build())
                .acceptCharset(Charset.forName("UTF-8"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProblemFilterDto.class)
                .blockOptional().get();

        return problemFilterDto;
    }

    private String makeQuery(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList) {

        StringBuilder query = new StringBuilder("");
        String tier = "bsgpdr";

        // "(tier:g1|tier:g2|tier:g3)&"
        if(tierList != null) {
            query.append("(");
            tierList.forEach(t -> {
                query.append("tier:").append(String.valueOf(t)).append("|");
            });
            query.deleteCharAt(query.length() - 1);
            query.append(")&");
        }
        // "(tag:bfs|tag:dfs)&"
        if(algoList != null) {
            query.append("(");
            algoList.forEach(a -> {
                query.append("tag:").append(a).append("|");
            });
            query.deleteCharAt(query.length() - 1);
            query.append(")&");
        }
        // "!(solved_by:seho27060|solved_by:min61037)"
        if(baekjoonIdList != null) {
            query.append("!(");
            baekjoonIdList.forEach(b -> {
                query.append("solved_by:").append(b).append("|");
            });
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
    public SolvedacSearchProblemDto recommendProblemFoMember(HttpServletRequest request) throws IOException {
        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        int page = 1;
        Long tier = 9L;
        String query = "";

        StringBuilder problemsIds = new StringBuilder("");

        // 로그인 사용자의 최근 푼 문제 번호 20개 가져오기
        ArrayList<Integer> latestSolvedProblemIdList = crawlingService.getRecentProblems(baekjoonId);

        for(int latestSolvedProblemId : latestSolvedProblemIdList){
            problemsIds.append(latestSolvedProblemId);
            problemsIds.append(",");
        }
        problemsIds.deleteCharAt(problemsIds.length()-1);
        // 문제 번호 20개로 문제 20개 불러오기
        System.out.println("latestSolvedProblemIdList"+ latestSolvedProblemIdList);
        System.out.println("problemIds"+ problemsIds);

        List<ProblemDto> problemDtoList;
        problemDtoList = webClient.get()
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
                .bodyToFlux(ProblemDto.class)
                .toStream()
                .collect(Collectors.toList());;

//        System.out.println(problemDtoList.get(0).getProblemId());
//        System.out.println(problemDtoList.get(problemDtoList.size()-1).getProblemId());
        // 가져온 문제들의 알고리즘 분류 카운트
        Map<String,Long> tagMap;
        
        // 카운트 상위 최대 3개의 알고리즘 분류 가져오기
        // 쿼리 생성
        // 옵션 주고 문제 api
        // 최대 50개중 랜덤으로 반환
//        Flux<ProblemDto> flux;
//
//        flux = webClient.get()
//                .uri(uriBuilder ->
//                        uriBuilder.path("/search/problem")
//                                .queryParam("query", query)
//                                .queryParam("page", page)
//                                .queryParam("sort", "solved")
//                                .queryParam("direction", "desc")
//                                .build())
//                .acceptCharset(StandardCharsets.UTF_8)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .onStatus(status ->
//                                status.is4xxClientError() || status.is5xxServerError(),
//                        clientResponse ->
//                                clientResponse
//                                        .bodyToMono(String.class)
//                                        .map(body -> new CustomException(ErrorCode.NOT_EXIST_MEMBER)))
//                .bodyToFlux(ProblemDto.class);
        return null;
    }
}
