package com.example.demo.controller;

import com.example.demo.domain.dto.member.MemberJoinRequestDto;
import com.example.demo.domain.dto.member.MemberResponseDto;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public void login(){

    }
    @GetMapping("/logout")
    public void logout() {

    }

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody MemberJoinRequestDto memberJoinRequestDto){
        System.out.println("------------------test");
        MemberResponseDto memberResponseDto = memberService.memberJoin(memberJoinRequestDto);
        return new ResponseEntity(memberResponseDto, HttpStatus.OK);
    }

    // 백준 연동
    @PostMapping("/interlock")
    public void interlock(){

    }
    // 연동을 위한 문자열 발급
    @GetMapping("/token")
    public void getToken() {

    }

    @DeleteMapping("/delete/{backjoonId}")
    public void memberWithdrawal(@PathVariable String bacjoonId){

    }

    @GetMapping("/find/{backjoonId}")
    public void findMember(@PathVariable String backjoonId){

    }

    @PutMapping("/modify")
    public void modifyMember(){

    }

}
