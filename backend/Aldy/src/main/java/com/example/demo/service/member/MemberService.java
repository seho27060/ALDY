package com.example.demo.service.member;

import com.example.demo.domain.dto.member.LoginRequestDto;
import com.example.demo.domain.dto.member.MemberJoinRequestDto;
import com.example.demo.domain.dto.member.MemberResponseDto;
import com.example.demo.domain.entity.Member.Token;

public interface MemberService {
    MemberResponseDto memberJoin(MemberJoinRequestDto memberJoinRequestDto);
//    MemberResponseDto findByBackjoonId(LoginRequestDto loginRequestDto);

    Token login(LoginRequestDto loginRequestDto);
    String withdrawal(String backjoonId);

    MemberResponseDto findMember(String backjoonId);
}
