package com.example.demo.service;

import com.example.demo.domain.dto.ProblemDto;
import com.example.demo.domain.dto.ProblemFilterDto;
import com.example.demo.domain.dto.member.response.SolvedacResponseDto;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

public interface SolvedacService {

    ProblemFilterDto filter(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList, int page);
    Optional<SolvedacResponseDto> solvedacMemberFindAPI(String baekjoonId);
}
