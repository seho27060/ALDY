package com.example.demo.controller;

import com.example.demo.domain.dto.member.request.*;
import com.example.demo.domain.dto.member.response.CodeReviewNumberResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;

import com.example.demo.exception.ErrorResponse;

import com.example.demo.service.member.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Member API - [담당자 : 박세호]", description = "회원정보 조회, 수정, 탈퇴")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 탈퇴 API", description = "로그인 유저의 회원 정보 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않습니다.",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/withdrawal")
    public ResponseEntity<MemberResponseDto> withdrawalMember(@RequestBody MemberPasswordRequestDto memberPasswordRequestDto, HttpServletRequest request){
        MemberResponseDto memberResponseDto = memberService.withdrawal(memberPasswordRequestDto, request);

        return new ResponseEntity<>(memberResponseDto,HttpStatus.OK);
    }
    @Operation(summary = "마이페이지", description = "로그인 유저의 회원 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없습니다.",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/mypage")
    public ResponseEntity<MemberResponseDto> findMember(HttpServletRequest request){
        MemberResponseDto memberResponseDto = memberService.findMember(request);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "마이페이지 리뷰한 코드, 리뷰 받은 코드 개수", description = "로그인 사용자가 리뷰한 코드(replyCodeReviewNumber), 로그인 사용자가 리뷰 받은 코드(answerCodeReviewNumber)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = CodeReviewNumberResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 요청입니다.",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/review")
    public ResponseEntity<CodeReviewNumberResponseDto> findCodeReviewNumberRelatedMember(HttpServletRequest request){
        CodeReviewNumberResponseDto codeReviewNumberResponseDto = memberService.findCodeReviewNumberRelatedMember(request);

        return new ResponseEntity<>(codeReviewNumberResponseDto, HttpStatus.OK);
    }
    @Operation(summary = "회원 정보 수정 API", description = "로그인 유저의 nickname, contact 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
    })
    @PutMapping("/info")
    public ResponseEntity<MemberResponseDto> modifyInfo(@RequestBody MemberModifyRequestDto memberModifyRequestDto, HttpServletRequest request){
        MemberResponseDto memberResponseDto = memberService.modifyInfo(memberModifyRequestDto, request);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }
    @Operation(summary = "회원 비밀번호 수정 API", description = "로그인 유저의 password 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
    })
    @PutMapping("/password")
    public ResponseEntity<MemberResponseDto> modifyPassword(@RequestBody MemberPasswordRequestDto memberPasswordRequestDto, HttpServletRequest request){
        MemberResponseDto memberResponseDto = memberService.modifyPassword(memberPasswordRequestDto, request);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

}

