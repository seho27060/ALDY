package com.example.demo.service;

import com.example.demo.domain.dto.member.MemberJoinRequestDto;
import com.example.demo.domain.dto.member.MemberResponseDto;
import com.example.demo.domain.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public MemberResponseDto memberJoin(MemberJoinRequestDto memberJoinRequestDto) {
        System.out.println("get :"+memberJoinRequestDto.getBackjoonId());
        System.out.println("get :"+memberJoinRequestDto.getContact());
        System.out.println("get :"+memberJoinRequestDto.getPassword());
        System.out.println("get :"+memberJoinRequestDto.getNickname());
        String rawPassword = memberJoinRequestDto.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

        Member member = new Member(memberJoinRequestDto,encodedPassword);
        memberRepository.save(member);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return memberResponseDto;
    }
}
