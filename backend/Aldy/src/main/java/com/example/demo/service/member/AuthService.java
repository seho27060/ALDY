package com.example.demo.service.member;

import com.example.demo.domain.dto.member.request.LoginRequestDto;
import com.example.demo.domain.dto.member.request.MemberRequestDto;
import com.example.demo.domain.dto.member.response.AuthStringResonseDto;
import com.example.demo.domain.dto.member.response.DoubleCheckResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Member.Token;

public interface AuthService {
    MemberResponseDto memberJoin(MemberRequestDto memberRequestDto);
    //    MemberResponseDto findByBackjoonId(LoginRequestDto loginRequestDto);
    Token login(LoginRequestDto loginRequestDto);
    void interlock(String backjoonId);

    DoubleCheckResponseDto doubleCheckNickname(String nickname);

    DoubleCheckResponseDto doubleCheckContact(String contact);
}
