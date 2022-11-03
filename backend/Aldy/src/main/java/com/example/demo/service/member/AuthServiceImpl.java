package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.request.LoginRequestDto;
import com.example.demo.domain.dto.member.request.MemberRequestDto;
import com.example.demo.domain.dto.member.response.DoubleCheckResponseDto;
import com.example.demo.domain.dto.member.response.InterlockResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.dto.member.response.SolvedacResponseDto;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.dto.member.response.TokenDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
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

    @Override
    public MemberResponseDto memberJoin(MemberRequestDto memberRequestDto) {
        String rawPassword = memberRequestDto.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

        if(memberRepository.existsByBaeckjoonId(memberRequestDto.getBaeckjoonId())){
            throw new CustomException(ErrorCode.ALREADY_JOIN);
        }

        HashOperations<String, String, String> valueOperations = stringRedisTemplate.opsForHash();
        Map<String, String> entries = valueOperations.entries(memberRequestDto.getBaeckjoonId());
        int tier = Integer.parseInt(Optional.ofNullable(entries.get("tier"))
                .orElse(String.valueOf(0)));
//        try{
//            tier = Integer.parseInt(entries.get("tier"));
//        } catch (Exception e ){
//            tier = 0;
//        }

        Member member = Member.builder()
                .baeckjoonId(memberRequestDto.getBaeckjoonId())
                .nickname(memberRequestDto.getNickname())
                .password(encodedPassword)
                .email(memberRequestDto.getEmail())
                .tier(tier)
                .build();

        memberRepository.save(member);
        valueOperations.delete(memberRequestDto.getBaeckjoonId(),"tier");
        return new MemberResponseDto(member);
    }

    @Override
    public TokenDto login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findByBaeckjoonId(loginRequestDto.getBaeckjoonId())
                .orElseThrow(
                        () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
                );
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getBaeckjoonId(),loginRequestDto.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            TokenDto tokenDto = jwtTokenProvider.createAccessToken(member.getBaeckjoonId(), List.of("ROLE_USER"));
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
    public String issueAuthString(String baeckjoonId) {
        SolvedacResponseDto solvedacResponseDto = solvedacMemberFindAPI(baeckjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

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
        hashOperations.put(baeckjoonId,"authString", String.valueOf(newWord));
        hashOperations.put(baeckjoonId,"tier", String.valueOf(solvedacResponseDto.getTier()));
        hashOperations.getOperations().expire(baeckjoonId,5L, TimeUnit.MINUTES);
//        hashOperations.(backjoonId, 5L, TimeUnit.MINUTES);
        return newWord.toString();
    }

    @Override
//    @CacheEvict(value = "String",key = "#baeckjoonId")
    public InterlockResponseDto interlock(String baeckjoonId) {

        SolvedacResponseDto solvedacResponseDto = solvedacMemberFindAPI(baeckjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<String> solvedacBio = List.of(solvedacResponseDto.getBio().split(" "));

        HashOperations<String, String, String> valueOperations = stringRedisTemplate.opsForHash();
        Map<String, String> entries = valueOperations.entries(baeckjoonId);
        String authString = entries.get("authString");
        valueOperations.delete(baeckjoonId,"authString");

        return new InterlockResponseDto(solvedacBio.get(solvedacBio.size()-1).equals(authString));
    }
    private Optional<SolvedacResponseDto> solvedacMemberFindAPI(String baeckjoonId){
        Optional<SolvedacResponseDto> mono;
        mono = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/user/show")
                                .queryParam("handle", baeckjoonId)
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
        return new DoubleCheckResponseDto(!memberRepository.existsByEmail(contact));
    }
}
