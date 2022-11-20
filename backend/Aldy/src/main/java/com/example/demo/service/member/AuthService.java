package com.example.demo.service.member;

import com.example.demo.domain.dto.member.request.LoginRequestDto;
import com.example.demo.domain.dto.member.request.MemberRequestDto;
import com.example.demo.domain.dto.member.response.DoubleCheckResponseDto;
import com.example.demo.domain.dto.member.response.InterlockResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.dto.member.response.TokenDto;

public interface AuthService {
    MemberResponseDto memberJoin(MemberRequestDto memberRequestDto);
    TokenDto login(LoginRequestDto loginRequestDto);

    String issueAuthString(String baekjoonId);
    InterlockResponseDto interlock(String baekjoonId);

    DoubleCheckResponseDto doubleCheckNickname(String nickname);

    DoubleCheckResponseDto doubleCheckContact(String contact);
}
