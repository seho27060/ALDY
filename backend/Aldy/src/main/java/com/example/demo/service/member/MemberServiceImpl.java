package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.request.LoginRequestDto;
import com.example.demo.domain.dto.member.request.MemberJoinRequestDto;
import com.example.demo.domain.dto.member.request.WithdrawalRequestDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Member.Token;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
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

import javax.servlet.http.HttpServletRequest;
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

        if(memberRepository.existsByBackjoonId(memberJoinRequestDto.getBackjoonId())){
            throw new CustomException(ErrorCode.ALREADY_JOIN);
        }

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
                        () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
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
            if (e.getClass().equals(BadCredentialsException.class)) {
                throw new BadCredentialsException("잘못된 비밀번호입니다");
            } else if (e.getClass().equals(DisabledException.class)) {
                throw new DisabledException("사용할 수 없는 계정입니다");
            } else if (e.getClass().equals(LockedException.class)) {
                throw new LockedException("잠긴 계정입니다");
            } else {
                throw e;
            }
        }
    }

    @Override
    public MemberResponseDto withdrawal(WithdrawalRequestDto withdrawalRequestDto, HttpServletRequest request) {
        String loginMember = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBackjoonId(loginMember).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        MemberResponseDto response;

        if(bCryptPasswordEncoder.matches( withdrawalRequestDto.getPassword(),member.getPassword())){
            memberRepository.delete(member);
            response = new MemberResponseDto(member);
        } else {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        return response;
    }

    @Override
    public MemberResponseDto findMember(String backjoonId) {
        Member member = memberRepository.findByBackjoonId(backjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return memberResponseDto;
    }

    @Override
    public MemberResponseDto modifyMember(MemberJoinRequestDto memberModifyRequestDto) {
        return null;
    }

//    @Override
//    public MemberResponseDto findByBackjoonId(LoginRequestDto loginRequestDto) {
//        MemberResponseDto memberResponseDto = new MemberResponseDto( memberRepository.findByBackjoonId(loginRequestDto.getBackjoonId()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않은 사용자 입니다.")));
//        return memberResponseDto;
//    }
}
