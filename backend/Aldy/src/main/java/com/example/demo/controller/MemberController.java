package com.example.demo.controller;

import com.example.demo.domain.dto.member.request.*;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.dto.member.response.TokenDto;
import com.example.demo.domain.dto.member.response.ValidateTokenResponseDto;
import com.example.demo.exception.ErrorResponse;
import com.example.demo.service.member.JwtService;
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
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Member API", description = "담당자 : 박세호 \n회원정보 조회, 수정, 탈퇴")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtService jwtService;

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
    @Operation(summary = "회원 조회 API", description = "검색 유저의 회원 정보 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없습니다.",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/find/{backjoonId}")
    public ResponseEntity<MemberResponseDto> findMember(@PathVariable String backjoonId){
        MemberResponseDto memberResponseDto = memberService.findMember(backjoonId);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
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
    @Operation(summary = "리프레쉬 토큰 재발급 API", description = "테스트중..")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않습니다."),
    })

    // refreshtoken 받음 -> 만료안되면 새로운 accesstoken 발급/ 만료된거면 다시 로그인하라고 예외던지기
    @PostMapping("/refresh")
    public ResponseEntity<ValidateTokenResponseDto> validateRefreshToken(@RequestBody ValidateTokenRequestDto validateTokenRequestDto){
        System.out.println(validateTokenRequestDto.getToken());

        ValidateTokenResponseDto validateTokenResponseDto = jwtService.validateRefreshToken(validateTokenRequestDto.getToken());
        System.out.println(validateTokenResponseDto);
        return new ResponseEntity<>(validateTokenResponseDto,HttpStatus.OK);
    }
}

