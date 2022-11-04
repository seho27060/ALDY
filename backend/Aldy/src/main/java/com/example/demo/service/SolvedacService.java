package com.example.demo.service;

import com.example.demo.domain.dto.ProblemFilterDto;
import com.example.demo.domain.dto.solvedac.SolvedacSearchProblemDto;
import com.example.demo.domain.dto.solvedac.response.SolvedacMemberResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SolvedacService {

    ProblemFilterDto filter(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList, int page);
    Optional<SolvedacMemberResponseDto> solvedacMemberFindAPI(String baekjoonId);

    SolvedacSearchProblemDto recommendProblemFoMember(HttpServletRequest request) throws IOException;
}
