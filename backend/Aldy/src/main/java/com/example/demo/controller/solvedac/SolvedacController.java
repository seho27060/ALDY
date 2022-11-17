package com.example.demo.controller.solvedac;

import com.example.demo.domain.dto.solvedac.SolvedacSearchProblemDto;
import com.example.demo.domain.dto.study.StudyInfoListDto;
import com.example.demo.service.solvedac.SolvedacService;
import com.example.demo.service.study.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "스터디 문제 추천 API", description = "[algoList : 알고리즘 목록], [tierList : 티어 목록], [backjoonIdList : 유저 목록]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = SolvedacSearchProblemDto.class))),
    })
    @GetMapping()
    public ResponseEntity<SolvedacSearchProblemDto> filterProblem(
            @RequestParam(required = false) List<String> algoList,
            @RequestParam(required = false) List<Integer> tierList,
            @RequestParam(required = false) List<String> baekjoonIdList,
            @RequestParam(defaultValue = "1") int page) {

        SolvedacSearchProblemDto problemFilterDto = solvedacService.filter(algoList, tierList, baekjoonIdList, page);

        return new ResponseEntity<>(problemFilterDto, HttpStatus.OK);

    }

    @Operation(summary = "문제 필터 페이지에 리스트 보내주기", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = StudyInfoListDto.class))),
    })
    @GetMapping("/list")
    public ResponseEntity<StudyInfoListDto> getList(@RequestParam Long studyId) {

        StudyInfoListDto studyInfoListDto = studyService.getStudyInfoList(studyId);

        return new ResponseEntity<>(studyInfoListDto, HttpStatus.OK);

    }

    @Operation(summary = "스터디 문제 검색 API", description = "[keyword : 검석애]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = SolvedacSearchProblemDto.class))),
    })
    @GetMapping("/search")
    public ResponseEntity<SolvedacSearchProblemDto> filterProblem(
            @RequestParam(required = false) String keyword
//            , @RequestParam(defaultValue = "1") int page
    ) {

        SolvedacSearchProblemDto solvedacSearchProblemDto = solvedacService.search(keyword);

        return new ResponseEntity<>(solvedacSearchProblemDto, HttpStatus.OK);

    }


}