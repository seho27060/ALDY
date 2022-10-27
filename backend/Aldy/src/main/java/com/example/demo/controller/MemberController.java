package com.example.demo.controller;

import com.example.demo.domain.dto.member.LoginRequestDto;
import com.example.demo.domain.dto.member.MemberJoinRequestDto;
import com.example.demo.domain.dto.member.MemberResponseDto;
import com.example.demo.domain.entity.Member.Token;
import com.example.demo.service.member.JwtService;
import com.example.demo.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto){
        Token token = memberService.login(loginRequestDto);
        return token;
    }

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody MemberJoinRequestDto memberJoinRequestDto){
        System.out.println("------------------test");
        MemberResponseDto memberResponseDto = memberService.memberJoin(memberJoinRequestDto);
        return new ResponseEntity(memberResponseDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity validateRefreshToken(@RequestBody HashMap<String, String> bodyJson){
        Map<String, String> map = jwtService.validateRefreshToken(bodyJson.get("refreshToken"));

//        if(map.get("status").equals("402")){
//            RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
//            return new ResponseEntity<RefreshApiResponseMessage>(refreshApiResponseMessage, HttpStatus.UNAUTHORIZED);
//        }
//
//        RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
//        return new ResponseEntity<RefreshApiResponseMessage>(refreshApiResponseMessage, HttpStatus.OK);

        return new ResponseEntity(map,HttpStatus.OK);
    }
    // 백준 연동
    @PostMapping("/interlock")
    public void interlock(){

    }
    // 연동을 위한 문자열 발급
    @GetMapping("/token")
    public String getToken() {
        return "TEST_TOKEN";
    }

    @DeleteMapping("/withdrawal/{backjoonId}")
    public ResponseEntity memberWithdrawal(@PathVariable String backjoonId){
        String response = memberService.withdrawal(backjoonId);

        return new ResponseEntity(response,HttpStatus.OK);
    }

    @GetMapping("/find/{backjoonId}")
    public ResponseEntity findMember(@PathVariable String backjoonId){
        MemberResponseDto memberResponseDto = memberService.findMember(backjoonId);
        return new ResponseEntity(memberResponseDto, HttpStatus.OK);
    }

    @PutMapping("/modify")
    public void modifyMember(){

    }

}
