package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.request.*;
import com.example.demo.domain.dto.member.response.DoubleCheckResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Member.Token;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.Member.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public MemberResponseDto withdrawal(MemberWithdrawalRequestDto memberWithdrawalRequestDto, HttpServletRequest request) {
        String loginMember = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBackjoonId(loginMember).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        MemberResponseDto response;

        if(bCryptPasswordEncoder.matches( memberWithdrawalRequestDto.getPassword(),member.getPassword())){
            memberRepository.delete(member);
            response = new MemberResponseDto(member);
        } else {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        return response;
    }

    @Override
    public MemberResponseDto findMember(String backjoonId) {
        Member member = memberRepository.findByBackjoonId(backjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return new MemberResponseDto(member);
    }

    @Override
    public MemberResponseDto modifyInfo(MemberModifyRequestDto memberModifyRequestDto, HttpServletRequest request) {
        System.out.println(">>>>>>>>>>"+jwtTokenProvider.getBackjoonId(request.getHeader("Authorization")));
        String loginMemberBackjoonId = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBackjoonId(loginMemberBackjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.modifyInfo(memberModifyRequestDto);
        return new MemberResponseDto(member);
    }

    @Override
    public MemberResponseDto modifyPassword(MemberPasswordRequestDto memberPasswordRequestDto, HttpServletRequest request) {
        String loginMemberBackjoonId = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBackjoonId(loginMemberBackjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        String rawPassword = memberPasswordRequestDto.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        member.modifyPassword(encodedPassword);
        return new MemberResponseDto(member);
    }


}
