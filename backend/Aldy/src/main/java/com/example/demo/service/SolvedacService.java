package com.example.demo.service;

import com.example.demo.domain.dto.ProblemDto;
import reactor.core.publisher.Flux;

import java.util.List;

public interface SolvedacService {

    List<ProblemDto> filter(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList);

}
