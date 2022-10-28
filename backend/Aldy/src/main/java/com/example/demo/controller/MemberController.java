package com.example.demo.controller;

import com.example.demo.domain.dto.member.request.*;
import com.example.demo.domain.dto.member.response.DoubleCheckResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Member.Token;
import com.example.demo.service.member.JwtService;
import com.example.demo.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("/withdrawal")
    public ResponseEntity<MemberResponseDto> withdrawalMember(@RequestBody MemberWithdrawalRequestDto memberWithdrawalRequestDto, HttpServletRequest request){
        MemberResponseDto memberResponseDto = memberService.withdrawal(memberWithdrawalRequestDto, request);

        return new ResponseEntity<>(memberResponseDto,HttpStatus.OK);
    }

    @GetMapping("/find/{backjoonId}")
    public ResponseEntity<MemberResponseDto> findMember(@PathVariable String backjoonId){
        MemberResponseDto memberResponseDto = memberService.findMember(backjoonId);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @PutMapping("/info")
    public ResponseEntity<MemberResponseDto> modifyInfo(@RequestBody MemberModifyRequestDto memberModifyRequestDto, HttpServletRequest request){
        MemberResponseDto memberResponseDto = memberService.modifyInfo(memberModifyRequestDto, request);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<MemberResponseDto> modifyPassword(@RequestBody MemberPasswordRequestDto memberPasswordRequestDto, HttpServletRequest request){
        MemberResponseDto memberResponseDto = memberService.modifyPassword(memberPasswordRequestDto, request);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> validateRefreshToken(@RequestBody HashMap<String, String> bodyJson){
        Map<String, String> map = jwtService.validateRefreshToken(bodyJson.get("refreshToken"));

//        if(map.get("status").equals("402")){
//            RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
//            return new ResponseEntity<RefreshApiResponseMessage>(refreshApiResponseMessage, HttpStatus.UNAUTHORIZED);
//        }
//
//        RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
//        return new ResponseEntity<RefreshApiResponseMessage>(refreshApiResponseMessage, HttpStatus.OK);

        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
