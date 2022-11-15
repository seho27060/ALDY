package com.example.demo.service.solvedac;

import com.example.demo.domain.dto.solvedac.ProblemWithTagDisplayNamesVo;
import com.example.demo.domain.dto.solvedac.SolvedacSearchProblemDto;
import com.example.demo.domain.dto.solvedac.response.SolvedacMemberResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SolvedacService {

    SolvedacSearchProblemDto filter(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList, int page);

    SolvedacSearchProblemDto search(String keyword);

    Optional<SolvedacMemberResponseDto> solvedacMemberFindAPI(String baekjoonId);

    ProblemWithTagDisplayNamesVo recommendProblem(HttpServletRequest request) throws IOException;

    void renewRecommendProblemList(HttpServletRequest request) throws IOException;

}
