package com.example.demo.controller;

import com.example.demo.domain.dto.member.request.LoginRequestDto;
import com.example.demo.domain.dto.member.request.MemberRequestDto;
import com.example.demo.domain.dto.member.response.AuthStringResonseDto;
import com.example.demo.domain.dto.member.response.DoubleCheckResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Member.Token;
import com.example.demo.service.member.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequestDto loginRequestDto){
        Token token = authService.login(loginRequestDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<MemberResponseDto> join(@RequestBody MemberRequestDto memberRequestDto){
        MemberResponseDto memberResponseDto = authService.memberJoin(memberRequestDto);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }
    @GetMapping("/token")
    public ResponseEntity<AuthStringResonseDto> issueAuthString(HttpServletRequest request) {
        return new ResponseEntity<>(new AuthStringResonseDto("TEST_AUTH_STRING"), HttpStatus.OK);
    }

    // 백준 연동
    @GetMapping("/interlock/{backjoonId}")
    public void interlock(@PathVariable String backjoonId){
       authService.interlock(backjoonId);
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<DoubleCheckResponseDto> doubleCheckBackjoonId(@PathVariable String nickname){
        DoubleCheckResponseDto doubleCheckResponseDto  = authService.doubleCheckNickname(nickname);
        return new ResponseEntity<>(doubleCheckResponseDto, HttpStatus.OK);
    }
    @GetMapping("/contact/{contact}")
    public ResponseEntity<DoubleCheckResponseDto> doubleCheckContact(@PathVariable String contact){
        DoubleCheckResponseDto doubleCheckResponseDto  = authService.doubleCheckContact(contact);
        return new ResponseEntity<>(doubleCheckResponseDto, HttpStatus.OK);
    }
}
