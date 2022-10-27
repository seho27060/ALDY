package com.example.demo.service.member;

import com.example.demo.domain.dto.member.request.LoginRequestDto;
import com.example.demo.domain.dto.member.request.MemberJoinRequestDto;
import com.example.demo.domain.dto.member.request.WithdrawalRequestDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Member.Token;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {
    MemberResponseDto memberJoin(MemberJoinRequestDto memberJoinRequestDto);
//    MemberResponseDto findByBackjoonId(LoginRequestDto loginRequestDto);
    Token login(LoginRequestDto loginRequestDto);
    MemberResponseDto withdrawal(WithdrawalRequestDto password, HttpServletRequest request);
    MemberResponseDto findMember(String backjoonId);
    MemberResponseDto modifyMember(MemberJoinRequestDto memberModifyRequestDto);
}
