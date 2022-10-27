package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.LoginRequestDto;
import com.example.demo.domain.dto.member.MemberJoinRequestDto;
import com.example.demo.domain.dto.member.MemberResponseDto;
import com.example.demo.domain.entity.Member.Token;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
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
        System.out.println(String.format("memberService login : %s, password : %s",loginRequestDto.getBackjoonId(),loginRequestDto.getPassword()));
        Member member = memberRepository.findByBackjoonId(loginRequestDto.getBackjoonId())
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                loginRequestDto.getBackjoonId()+"가입되지 않은 backjoonId 입니다."
                        )
                );
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getBackjoonId(),loginRequestDto.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Token tokenDto = jwtTokenProvider.createAccessToken(member.getBackjoonId(), List.of("ROLE_USER"));
            System.out.println(">>>>>>member :"+member);
            System.out.println(">>>>>>token :"+tokenDto);
            jwtService.login(tokenDto);
            return tokenDto;
        }catch (DisabledException | LockedException | BadCredentialsException e){
            String status;
            if (e.getClass().equals(BadCredentialsException.class)) {
                status = "invalid-password";
            } else if (e.getClass().equals(DisabledException.class)) {
                status = "locked";
            } else if (e.getClass().equals(LockedException.class)) {
                status = "disable";
            } else {
                status = "unknown";
            }
            System.out.println("인증 예외 : "+status);
        }
        return null;
    }

    @Override
    public String withdrawal(String backjoonId) {
        Member member = memberRepository.findByBackjoonId(backjoonId).orElseThrow(() -> new IllegalArgumentException(backjoonId+"는 가입되지 않은 backjoonId 입니다."));
        memberRepository.delete(member);
        return null;
    }

    @Override
    public MemberResponseDto findMember(String backjoonId) {
        Member member = memberRepository.findByBackjoonId(backjoonId).orElseThrow(() -> new IllegalArgumentException(">>>>"+backjoonId+"는 가입되지 않은 backjoonId 입니다."));
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return memberResponseDto;
    }

//    @Override
//    public MemberResponseDto findByBackjoonId(LoginRequestDto loginRequestDto) {
//        MemberResponseDto memberResponseDto = new MemberResponseDto( memberRepository.findByBackjoonId(loginRequestDto.getBackjoonId()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않은 사용자 입니다.")));
//        return memberResponseDto;
//    }
}
