package com.example.demo.service.member;

import com.example.demo.domain.dto.member.request.*;
import com.example.demo.domain.dto.member.response.DoubleCheckResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Member.Token;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {

    MemberResponseDto withdrawal(MemberWithdrawalRequestDto password, HttpServletRequest request);
    MemberResponseDto findMember(String backjoonId);
    MemberResponseDto modifyInfo(MemberModifyRequestDto memberModifyRequestDto, HttpServletRequest request);
    MemberResponseDto modifyPassword(MemberPasswordRequestDto memberPasswordRequestDto, HttpServletRequest request);


}
