package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.response.TokenDto;
import com.example.demo.domain.entity.Member.RefreshToken;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.Member.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtServiceImpl implements JwtService{

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void login(TokenDto tokenDto) {
        RefreshToken refreshToken = RefreshToken.builder()
                .keyBackjoonId(tokenDto.getKey())
                .refreshToken(tokenDto.getRefreshToken())
                .build();

        String loginUserBackjoonId = refreshToken.getKeyBackjoonId();
        if(refreshTokenRepository.existsByKeyBackjoonId(loginUserBackjoonId)){
            refreshTokenRepository.deleteByKeyBackjoonId(loginUserBackjoonId);
        }
        refreshTokenRepository.save(refreshToken);
    }
    @Override
    public Map<String, String> validateRefreshToken(String refreshToken) {
        RefreshToken refreshToken1 = getRefreshToken(refreshToken);
        String createdAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken1);

        return createRefreshJson(createdAccessToken);
    }
    @Override
    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElse(null);
    }

    @Override
    public Map<String, String> createRefreshJson(String createdAccessToken) {
        Map<String,String> map = new HashMap<>();
        if(createdAccessToken == null){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        } else {
            map.put("status", "200");
            map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
            map.put("accessToken", createdAccessToken);
        }
        //기존에 존재하는 accessToken 제거
        return map;
    }
}

