package com.example.demo.service.solvedac;

import com.example.demo.domain.dto.solvedac.SolvedacSearchProblemDto;
import com.example.demo.domain.dto.solvedac.response.SolvedacMemberResponseDto;
import com.example.demo.domain.dto.solvedac.ProblemVo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SolvedacService {

    SolvedacSearchProblemDto filter(List<String> algoList, List<Integer> tierList, List<String> baekjoonIdList, int page);
    Optional<SolvedacMemberResponseDto> solvedacMemberFindAPI(String baekjoonId);
    ProblemVo recommendProblemForMember(HttpServletRequest request) throws IOException;

}
