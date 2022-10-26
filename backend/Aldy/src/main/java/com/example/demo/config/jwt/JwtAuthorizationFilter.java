//package com.example.demo.config.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.example.demo.domain.entity.Member.Member;
//import com.example.demo.repository.Member.MemberRepository;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Optional;
//
//public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//    private MemberRepository memberRepository;
//
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository){
//        super(authenticationManager);
//        this.memberRepository = memberRepository;
//    }
//
//    @Override
//    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
//        String header = request.getHeader(JwtProperties.HEADER_STRING);
//        if(header==null || !header.startsWith(JwtProperties.TOKEN_PREFIX)){
//            chain.doFilter(request,response);
//            return;
//        }
//        System.out.println("header : " + header);
//        String token = request.getHeader(JwtProperties.HEADER_STRING)
//                .replace(JwtProperties.TOKEN_PREFIX, "");
//
//        // 토큰 검증
//        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
//                .getClaim("username").asString();
//
//        // username 값이 backjoonId가 나오나?
//        if(username != null){
//            Member member = memberRepository.findByBackjoonId(username).orElse(null);
//
//            // 인증이 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위함.
//            PrincipalDetails principalDetails = new PrincipalDetails(member);
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    principalDetails,
//                    null,
//                    principalDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        chain.doFilter(request, response);
//    }
//}
