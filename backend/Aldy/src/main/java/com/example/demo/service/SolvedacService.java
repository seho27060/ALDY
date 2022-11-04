package com.example.demo.service;

import com.example.demo.domain.dto.ProblemDto;
import com.example.demo.domain.dto.solvedac.response.SolvedacMemberResponseDto;

import java.util.List;
import java.util.Optional;

public interface SolvedacService {

    List<ProblemDto> filter(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList);
    Optional<SolvedacMemberResponseDto> solvedacMemberFindAPI(String baekjoonId);
}
