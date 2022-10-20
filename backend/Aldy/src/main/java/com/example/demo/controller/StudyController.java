package com.example.demo.controller;

import com.example.demo.domain.dto.CreateStudyPostDto;
import com.example.demo.domain.dto.StudyDto;
import com.example.demo.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Study API", description = "스터디 관련 API")
@RequestMapping(name = "/api/study")
public class StudyController {

    private final StudyService studyService;

    @Operation(summary = "스터디 생성 API", description = "스터디 생성 관련 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping()
    public ResponseEntity<StudyDto> createStudy(@RequestBody CreateStudyPostDto requestDto) {

        StudyDto studyDto = studyService.createStudy(requestDto);

        return new ResponseEntity<>(studyDto, HttpStatus.OK);

    }

    @Operation(summary = "스터디 조회 API", description = "[page : 페이지], [size : 페이지 당 정보 개수]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "스터디 없음"),
    })
    @GetMapping()
    public ResponseEntity<> getAllStudyPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Page<StudyDto> studyDtoPage = studyService.getStudyPage(page, size, keyword);
    }


}
