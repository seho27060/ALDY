package com.example.demo.service.solvedac;

import com.example.demo.domain.dto.solvedac.SolvedProblemDto;
import com.example.demo.domain.dto.solvedac.response.MemberProblemRecommendationResponseDto;
import com.example.demo.domain.dto.study.ProblemFilterDto;
import com.example.demo.domain.dto.solvedac.SolvedacSearchProblemDto;
import com.example.demo.domain.dto.solvedac.response.SolvedacMemberResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SolvedacService {

    ProblemFilterDto filter(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList, int page);
    Optional<SolvedacMemberResponseDto> solvedacMemberFindAPI(String baekjoonId);
    MemberProblemRecommendationResponseDto recommendProblemForMember(HttpServletRequest request) throws IOException;
}
