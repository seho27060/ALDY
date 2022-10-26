package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.LoginRequestDto;
import com.example.demo.domain.dto.member.MemberJoinRequestDto;
import com.example.demo.domain.dto.member.MemberResponseDto;
import com.example.demo.domain.dto.member.Token;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private JwtService jwtService;
    private final JwtTokenProvider jwtTokenProvider;
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

    @Override
    public Token login(LoginRequestDto loginRequestDto) {

        Member member = memberRepository.findByBackjoonId(loginRequestDto.getBackjoonId()).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 backjoonId 입니다."));
        Token tokenDto = jwtTokenProvider.createAccessToken(member.getBackjoonId(), List.of("ROLE_USER"));
        jwtService.login(tokenDto);
        return tokenDto;
    }

//    @Override
//    public MemberResponseDto findByBackjoonId(LoginRequestDto loginRequestDto) {
//        MemberResponseDto memberResponseDto = new MemberResponseDto( memberRepository.findByBackjoonId(loginRequestDto.getBackjoonId()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않은 사용자 입니다.")));
//        return memberResponseDto;
//    }
}
