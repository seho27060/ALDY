package com.example.demo.controller;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.MemberInStudyChangeAuthDto;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "MemberInStudy API", description = "Study 입장, 퇴장 API, [담당자 : 홍석호]")
@RequestMapping(value = "/api/memberinstudy")
public class MemberInStudyController {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberInStudyService memberInStudyService;

    @Operation(summary = "스터디 가입 신청 API", description = "스터디 가입 신청 관련 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "403", description = "MEMBER_LIMIT_EXCEEDED"),
            @ApiResponse(responseCode = "403", description = "UNAUTHORIZED_REQUEST"),
            @ApiResponse(responseCode = "404", description = "STUDY_NOT_FOUND"),
            @ApiResponse(responseCode = "404", description = "MEMBER_NOT_FOUND"),
            @ApiResponse(responseCode = "409", description = "DUPLICATE_RESOURCE"),
    })
    @PostMapping()
    public ResponseEntity applicateStudy(@RequestBody ApplicateStudyRequestDto requestDto, HttpServletRequest request) {

        String loginMember = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));

        MemberInStudyDto memberInStudyDto = memberInStudyService.applicateStudy(requestDto, loginMember);

        return new ResponseEntity<>(memberInStudyDto, HttpStatus.OK);

    }

    @Operation(summary = "스터디원 리스트 보여주기 API", description = "스터디원 리스트 보여주기 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
    })
    @GetMapping("/{studyId}")
    public ResponseEntity getAllMemberInStudy(@PathVariable("studyId") Long studyId) {

        List<MemberInStudyDto> memberInStudyDtoList = memberInStudyService.getAllMemberInStudy(studyId);

        return new ResponseEntity<>(memberInStudyDtoList, HttpStatus.OK);

    }

    @Operation(summary = "스터디원 가입 수락 API", description = "[RequestBody : 권한 수정할 스터디, 유저 정보], [loginMemberId : 로그인 한 유저 정보]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "403", description = "UNAUTHORIZED_REQUEST"),
            @ApiResponse(responseCode = "404", description = "MEMBERINSTUDY_NOT_FOUND"),
    })
    @PatchMapping("accept")
    public ResponseEntity acceptMember(@RequestBody MemberInStudyChangeAuthDto requestDto, HttpServletRequest request) {

        String loginMember = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));

        MemberInStudyDto memberInStudyDto = memberInStudyService.changeAuth(requestDto, loginMember, 2);

        return new ResponseEntity<>(memberInStudyDto, HttpStatus.OK);

    }

    @Operation(summary = "스터디원 강퇴 API", description = "[RequestBody : 권한 수정할 스터디, 유저 정보], [loginMemberId : 로그인 한 유저 정보]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "403", description = "UNAUTHORIZED_REQUEST"),
            @ApiResponse(responseCode = "404", description = "MEMBERINSTUDY_NOT_FOUND"),
    })
    @PatchMapping("kick")
    public ResponseEntity kickMember(@RequestBody MemberInStudyChangeAuthDto requestDto, HttpServletRequest request) {

        String loginMember = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));

        MemberInStudyDto memberInStudyDto = memberInStudyService.changeAuth(requestDto, loginMember, 0);

        return new ResponseEntity<>(memberInStudyDto, HttpStatus.OK);

    }

    @Operation(summary = "스터디원 가입 거절 API", description = "[RequestBody : 권한 수정할 스터디, 유저 정보], [loginMemberId : 로그인 한 유저 정보]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "403", description = "UNAUTHORIZED_REQUEST"),
            @ApiResponse(responseCode = "404", description = "MEMBERINSTUDY_NOT_FOUND"),
    })
    @DeleteMapping("reject")
    public ResponseEntity rejectMember(@RequestBody MemberInStudyChangeAuthDto requestDto, HttpServletRequest request) {

        String loginMember = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));

        memberInStudyService.rejectMember(requestDto, loginMember);

        return new ResponseEntity<>(HttpStatus.OK);

    }


}
