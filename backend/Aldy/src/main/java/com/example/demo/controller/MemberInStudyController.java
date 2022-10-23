package com.example.demo.controller;

import com.example.demo.domain.dto.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.MemberInStudyDto;
import com.example.demo.service.MemberInStudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "MemberInStudy API", description = "Study 입장, 퇴장 API")
@RequestMapping(value = "/api/memberinstudy")
public class MemberInStudyController {

    private final MemberInStudyService memberInStudyService;

    @Operation(summary = "스터디 가입 신청 API", description = "스터디 가입 신청 관련 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping()
    public ResponseEntity applicateStudy(@RequestBody ApplicateStudyRequestDto requestDto) {

        MemberInStudyDto memberInStudyDto = memberInStudyService.applicateStudy(requestDto);

        return new ResponseEntity(memberInStudyDto, HttpStatus.OK);

    }

    @Operation(summary = "스터디원 리스트 보여주기 API", description = "스터디원 리스트 보여주기 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @GetMapping("/{studyId}")
    public ResponseEntity getAllMemberInStudy(@PathVariable("studyId") Long studyId) {

        List<MemberInStudyDto> memberInStudyDtoList = memberInStudyService.getAllMemberInStudy(studyId);

        return new ResponseEntity(memberInStudyDtoList, HttpStatus.OK);

    }
}
