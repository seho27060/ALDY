package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.request.LoginRequestDto;
import com.example.demo.domain.dto.member.request.MemberRequestDto;
import com.example.demo.domain.dto.member.response.DoubleCheckResponseDto;
import com.example.demo.domain.dto.member.response.InterlockResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.dto.solvedac.response.SolvedacMemberResponseDto;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.dto.member.response.TokenDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.service.solvedac.SolvedacService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    private final StringRedisTemplate stringRedisTemplate;
    private final SolvedacService solvedacService;

    @Override
    public MemberResponseDto memberJoin(MemberRequestDto memberRequestDto) {
        String rawPassword = memberRequestDto.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

        HashOperations<String, String, String> valueOperations = stringRedisTemplate.opsForHash();

        if(memberRepository.existsByBaekjoonId(memberRequestDto.getBaekjoonId())){
            throw new CustomException(ErrorCode.ALREADY_JOIN);
        }

        Map<String, String> entries = valueOperations.entries(memberRequestDto.getBaekjoonId());
        Long tier = Long.valueOf(Optional.ofNullable(entries.get("tier"))
                .orElseGet(()->String.valueOf(0L)));
        System.out.printf(String.format("entries: %s get tier:%d",entries.get("tier"),tier));
        Member member = Member.builder()
                .baekjoonId(memberRequestDto.getBaekjoonId())
                .nickname(memberRequestDto.getNickname())
                .password(encodedPassword)
                .email(memberRequestDto.getEmail())
                .tier(tier)
                .build();

        memberRepository.save(member);
        valueOperations.delete(memberRequestDto.getBaekjoonId(),"tier");
        valueOperations.delete(memberRequestDto.getBaekjoonId(),"authString");
        return new MemberResponseDto(member);
    }

    @Override
    public TokenDto login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findByBaekjoonId(loginRequestDto.getBaekjoonId())
                .orElseThrow(
                        () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
                );
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getBaekjoonId(),loginRequestDto.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            TokenDto tokenDto = jwtTokenProvider.createAccessToken(member.getBaekjoonId(), List.of("ROLE_USER"));
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
    public String issueAuthString(String baekjoonId) {
        SolvedacMemberResponseDto solvedacMemberResponseDto = solvedacService.solvedacMemberFindAPI(baekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        if(memberRepository.findByBaekjoonId(baekjoonId).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_JOIN);
        }

        Random random = new Random();
        int length = 7;

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
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();

        hashOperations.put(baekjoonId,"authString", String.valueOf(newWord));
        hashOperations.put(baekjoonId,"tier", String.valueOf(solvedacMemberResponseDto.getTier()));
//        hashOperations.getOperations().expire(baekjoonId,7,TimeUnit.DAYS);
        hashOperations.getOperations().expire(baekjoonId,5L, TimeUnit.MINUTES);
        System.out.printf("issue %s %s",baekjoonId,newWord);
        return newWord.toString();
    }

    @Override
    public InterlockResponseDto interlock(String baekjoonId) {

        SolvedacMemberResponseDto solvedacMemberResponseDto = solvedacService.solvedacMemberFindAPI(baekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<String> solvedacBio = List.of(solvedacMemberResponseDto.getBio().split(" "));
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        Map<String, String> entries = hashOperations.entries(baekjoonId);
        String authString = entries.get("authString");

        
        return new InterlockResponseDto(solvedacBio.get(solvedacBio.size()-1).equals(authString));
    }

    @Override
    public DoubleCheckResponseDto doubleCheckNickname(String nickname) {
        return new DoubleCheckResponseDto(!memberRepository.existsByNickname(nickname));
    }

    @Override
    public DoubleCheckResponseDto doubleCheckContact(String contact) {
        return new DoubleCheckResponseDto(!memberRepository.existsByEmail(contact));
    }
}
