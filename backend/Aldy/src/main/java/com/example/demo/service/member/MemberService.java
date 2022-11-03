package com.example.demo.service.member;

import com.example.demo.domain.dto.member.request.*;
import com.example.demo.domain.dto.member.response.CodeNumberRelatedMemberResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {

    MemberResponseDto withdrawal(MemberPasswordRequestDto password, HttpServletRequest request);
    MemberResponseDto findMember(String baekjoonId);
    MemberResponseDto modifyInfo(MemberModifyRequestDto memberModifyRequestDto, HttpServletRequest request);
    MemberResponseDto modifyPassword(MemberPasswordRequestDto memberPasswordRequestDto, HttpServletRequest request);
    CodeNumberRelatedMemberResponseDto findCodeNumberRelatedMember(String baekjoonId);
}
