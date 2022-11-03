package com.example.demo.service;

import com.example.demo.domain.dto.ProblemDto;
import reactor.core.publisher.Flux;

import java.util.List;

public interface SolvedacService {

    List<ProblemDto> filter(List<String> algoList, List<Integer> tierList, List<String> backjoonIdList);
//    내가 최근에 푼 문제 리스트를 받음
//    전체 문제 태그가 존재하면
//
    List<String> getProblemInfo(int problemId);
}
