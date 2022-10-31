package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.request.LoginRequestDto;
import com.example.demo.domain.dto.member.request.MemberRequestDto;
import com.example.demo.domain.dto.member.response.DoubleCheckResponseDto;
import com.example.demo.domain.dto.member.response.InterlockResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.Member.Token;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService{
    private final MemberRepository memberRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public MemberResponseDto memberJoin(MemberRequestDto memberRequestDto) {
        System.out.println("get :"+ memberRequestDto.getBackjoonId());
        System.out.println("get :"+ memberRequestDto.getContact());
        System.out.println("get :"+ memberRequestDto.getPassword());
        System.out.println("get :"+ memberRequestDto.getNickname());

        String rawPassword = memberRequestDto.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

        if(memberRepository.existsByBackjoonId(memberRequestDto.getBackjoonId())){
            throw new CustomException(ErrorCode.ALREADY_JOIN);
        }

        Member member = Member.builder()
                .backjoonId(memberRequestDto.getBackjoonId())
                .nickname(memberRequestDto.getNickname())
                .password(encodedPassword)
                .contact(memberRequestDto.getContact()).build();

        memberRepository.save(member);

        return new MemberResponseDto(member);
    }

    @Override
    public Token login(LoginRequestDto loginRequestDto) {
        System.out.printf("memberService login : %s, password : %s%n",loginRequestDto.getBackjoonId(),loginRequestDto.getPassword());
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
            System.out.println("로그인 예외 : "+e);
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
    public String issueAuthString(String backjoonId) {
        Random random = new Random();
        int length = random.nextInt(5)+5;

        StringBuilder newWord = new StringBuilder("");
        for (int i = 0; i < length; i++) {
            int choice = random.nextInt(3);
            switch(choice) {
                case 0:
                    newWord.append((char)((int)random.nextInt(25)+97));
                    break;
                case 1:
                    newWord.append((char)((int)random.nextInt(25)+65));
                    break;
                case 2:
                    newWord.append((char)((int)random.nextInt(10)+48));
                    break;
                default:
                    break;
            }
        }
        return newWord.toString();
    }

    @Override
    public InterlockResponseDto interlock(String backjoonId) {
        // api 로 요청 후 반환값에서
        return new InterlockResponseDto(false);
    }

    @Override
    public DoubleCheckResponseDto doubleCheckNickname(String Nickname) {
        return new DoubleCheckResponseDto(memberRepository.existsByNickname(Nickname));
    }

    @Override
    public DoubleCheckResponseDto doubleCheckContact(String contact) {
        return new DoubleCheckResponseDto(memberRepository.existsByContact(contact));
    }
}
