package com.example.demo.service.solvedac;

import com.example.demo.domain.dto.study.ProblemFilterDto;
import com.example.demo.domain.dto.member.response.SolvedacResponseDto;

import java.util.List;
import java.util.Optional;

public interface SolvedacService {

    ProblemFilterDto filter(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList, int page);
    Optional<SolvedacResponseDto> solvedacMemberFindAPI(String baekjoonId);
}
