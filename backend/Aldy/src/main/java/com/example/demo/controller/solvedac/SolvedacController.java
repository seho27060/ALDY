package com.example.demo.controller.solvedac;

import com.example.demo.domain.dto.solvedac.SolvedacSearchProblemDto;
import com.example.demo.domain.dto.study.StudyInfoListDto;
import com.example.demo.service.solvedac.SolvedacService;
import com.example.demo.service.study.StudyService;
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

    private final StudyService studyService;

//    private final WebClient webClient;

    @Operation(summary = "스터디 문제 추천 API", description = "[algoList : 알고리즘 목록], [tierList : 티어 목록], [backjoonIdList : 유저 목록]")
    @GetMapping()
    public ResponseEntity<SolvedacSearchProblemDto> filterProblem(
            @RequestParam(required = false) List<String> algoList,
            @RequestParam(required = false) List<Integer> tierList,
            @RequestParam(required = false) List<String> backjoonIdList,
            @RequestParam(defaultValue = "1") int page) {

        SolvedacSearchProblemDto problemFilterDto = solvedacService.filter(algoList, tierList, backjoonIdList, page);

        return new ResponseEntity<>(problemFilterDto, HttpStatus.OK);

    }

    @Operation(summary = "문제 필터 페이지에 리스트 보내주기", description = "")
    @GetMapping("/list")
    public ResponseEntity<StudyInfoListDto> getList(@RequestParam Long studyId) {

        StudyInfoListDto studyInfoListDto = studyService.getStudyInfoList(studyId);

        return new ResponseEntity<>(studyInfoListDto, HttpStatus.OK);

    }

}