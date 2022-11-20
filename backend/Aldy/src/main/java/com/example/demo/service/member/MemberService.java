package com.example.demo.service.member;

import com.example.demo.domain.dto.member.request.*;
import com.example.demo.domain.dto.member.response.CodeReviewNumberResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {

    MemberResponseDto withdrawal(MemberPasswordRequestDto password, HttpServletRequest request);
    MemberResponseDto findMember(HttpServletRequest request);
    MemberResponseDto modifyNickname(MemberModifyNicknameRequestDto memberModifyNicknameRequestDto, HttpServletRequest request);
    MemberResponseDto modifyEmail(MemberModifyEmailRequestDto memberModifyEmailRequestDto, HttpServletRequest request);
    MemberResponseDto modifyPassword(MemberPasswordRequestDto memberPasswordRequestDto, HttpServletRequest request);
    CodeReviewNumberResponseDto findCodeReviewNumberRelatedMember(HttpServletRequest request);
    MemberResponseDto renewTier(HttpServletRequest request);

}
