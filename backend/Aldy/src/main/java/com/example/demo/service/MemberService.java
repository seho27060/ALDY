package com.example.demo.service;

import com.example.demo.domain.dto.member.MemberJoinRequestDto;
import com.example.demo.domain.dto.member.MemberResponseDto;

public interface MemberService {
    MemberResponseDto memberJoin(MemberJoinRequestDto memberJoinRequestDto);

}
