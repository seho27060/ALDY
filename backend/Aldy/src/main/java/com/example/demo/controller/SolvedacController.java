package com.example.demo.controller;

import com.example.demo.domain.dto.ProblemDto;
import com.example.demo.service.SolvedacService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Solvedac API", description = "백준 문제 연동 API")
@RequestMapping(value = "/api/solvedac")
public class SolvedacController {

    private final SolvedacService solvedacService;

//    private final WebClient webClient;

    @Operation(summary = "스터디 문제 추천 API", description = "[algoList : 알고리즘 목록], [tierList : 티어 목록], [backjoonIdList : 유저 목록]")
    @GetMapping()
    public ResponseEntity filterProblem(
            @RequestParam(required = false) List<String> algoList,
            @RequestParam(required = false) List<Integer> tierList,
            @RequestParam(required = false) List<String> backjoonIdList) {

//        List<ProblemDto> test = solvedacService.filter("(tier:g1|tier:g2|tier:g3)&(tag:bfs|tag:dfs)&!(solved_by:seho27060|solved_by:min61037)");
//
//        return new ResponseEntity(test, HttpStatus.OK);

        List<ProblemDto> problemDtoList = solvedacService.filter(algoList, tierList, backjoonIdList);

        return new ResponseEntity(problemDtoList, HttpStatus.OK);

    }

}