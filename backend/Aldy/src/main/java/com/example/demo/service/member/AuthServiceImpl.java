package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.request.LoginRequestDto;
import com.example.demo.domain.dto.member.request.MemberRequestDto;
import com.example.demo.domain.dto.member.response.DoubleCheckResponseDto;
import com.example.demo.domain.dto.member.response.InterlockResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.dto.member.response.SolvedacResponseDto;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.Member.Token;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService{
    private final MemberRepository memberRepository;

    private final WebClient webClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
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
                System.out.println(e.getClass());
                throw new CustomException(ErrorCode.WRONG_PASSWORD);
            } else if (e.getClass().equals(DisabledException.class)) {
                System.out.println(e.getClass());
                throw new CustomException(ErrorCode.DISABLED);
            } else if (e.getClass().equals(LockedException.class)) {
                System.out.println(e.getClass());
                throw new CustomException(ErrorCode.LOCKED);
            } else {
                throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
            }
        }
    }

    @Override
    public String issueAuthString(String backjoonId) {
        Optional<SolvedacResponseDto> mono;
        mono = SolvedacMemberFindAPI(backjoonId);
        SolvedacResponseDto solvedacResponseDto = mono.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

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
        Optional<SolvedacResponseDto> mono;
        mono = SolvedacMemberFindAPI(backjoonId);
        System.out.println(mono);
        SolvedacResponseDto solvedacResponseDto = mono.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        System.out.println(solvedacResponseDto.getBio());

        List<String> solvedacBio = List.of(solvedacResponseDto.getBio().split(" "));
        String testAuthString = "TEST_AUTH_STRING";
        System.out.printf("test AuthString :%s get AuthString :%s%n",testAuthString,solvedacBio.get(solvedacBio.size()-1));
        // api 로 요청 후 반환값에서

        return new InterlockResponseDto(solvedacBio.get(solvedacBio.size()-1).equals(testAuthString));
    }
    private Optional<SolvedacResponseDto> SolvedacMemberFindAPI(String backjoonId){
        Optional<SolvedacResponseDto> mono;
        mono = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/user/show")
                                .queryParam("handle", backjoonId)
                                .build())
                .acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status ->
                                status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse ->
                                clientResponse
                                        .bodyToMono(String.class)
                                        .map(body -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)))
                .bodyToMono(SolvedacResponseDto.class)
                .flux()
                .toStream()
                .findFirst();
        return mono;
    }
    @Override
    public DoubleCheckResponseDto doubleCheckNickname(String nickname) {
        return new DoubleCheckResponseDto(!memberRepository.existsByNickname(nickname));
    }

    @Override
    public DoubleCheckResponseDto doubleCheckContact(String contact) {
        return new DoubleCheckResponseDto(!memberRepository.existsByContact(contact));
    }
}
