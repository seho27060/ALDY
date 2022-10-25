package com.example.demo.config.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.domain.dto.member.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

import com.auth0.jwt.JWT;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletRequest response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 진입");

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;
        try{
            loginRequestDto = objectMapper.readValue(request.getInputStream(),LoginRequestDto.class);
        } catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("JwtAuthenticationFilter :"+loginRequestDto);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getBackjoonId(),loginRequestDto.getPassword());

        System.out.println("JwtAuthenticationFilter : 토큰 생성 완료");
        /*
         authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
		 loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
		 UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
		 UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
		 Authentication 객체를 만들어서 필터체인으로 리턴해준다.
//
		 Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
		 Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
		 결론은 인증 프로바이더에게 알려줄 필요가 없음.
         */

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("Authentication : "+principalDetails.getMember().getBackjoonId());
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException{

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getMember().getId())
                .withClaim("username", principalDetails.getMember().getBackjoonId())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
    }

}
