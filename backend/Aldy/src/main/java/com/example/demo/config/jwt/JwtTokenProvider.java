package com.example.demo.config.jwt;

import com.example.demo.config.auth.CustomUserDetailsService;
import com.example.demo.domain.dto.member.response.TokenDto;
import com.example.demo.domain.entity.Member.RefreshToken;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final String accessSecretKey = "killtheSpringSecurity2";

    private final long accessTokenValidTime = Duration.ofMinutes(60).toMillis();

    private final String refreshSecretKey = "killtheSpringSecurity3";

    private long refreshTokenValidTime = Duration.ofDays(7).toMillis();
    private final CustomUserDetailsService userDetailsService;


    public TokenDto createAccessToken(String baeckjoonId, List<String> roles) {

        Claims claims = Jwts.claims().setSubject(baeckjoonId); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        //Refresh Token
        String refreshToken =  Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        return TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).key(baeckjoonId).build();

    }
    public String validateRefreshToken(RefreshToken refreshTokenObj){

        // refresh 객체에서 refreshToken 추출
        String refreshToken = refreshTokenObj.getRefreshToken();
        try {
            // 검증
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);

            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
            if (!claims.getBody().getExpiration().before(new Date())) {
                return recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
            }
        }catch (Exception e) {
            throw new CustomException(ErrorCode.REFRESHTOKEN_EXPIRED);
            //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
        }
        return null;
    }
    public String recreationAccessToken(String baeckjoonid, Object roles){

        Claims claims = Jwts.claims().setSubject(baeckjoonid); // JWT payload 에 저장되는 정보단위
        claims.put("roles", "ROLE_USER"); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        return accessToken;
    }
    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getBaekjoonId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원정보 추출
    public String getBaekjoonId(String token){
        return Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token.replaceAll("^Bearer( )*","")).getBody().getSubject();
    }

    // Request 의 Header에서 token의 값을 가져옴
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String jwtToken){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(jwtToken.replaceAll("^Bearer( )*",""));
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e){
            System.out.println("만료된 토큰입니다. exception : "+e);
            return false;
        }
    }
}
