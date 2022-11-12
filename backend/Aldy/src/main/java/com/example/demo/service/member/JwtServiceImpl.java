package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.response.TokenDto;
import com.example.demo.domain.dto.member.response.ValidateTokenResponseDto;
import com.example.demo.domain.entity.Member.RefreshToken;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.member.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtServiceImpl implements JwtService{

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void login(TokenDto tokenDto) {
        RefreshToken refreshToken = RefreshToken.builder()
                .baekjoonId(tokenDto.getKey())
                .refreshToken(tokenDto.getRefreshToken())
                .build();

        String loginUserBaekjoonId = refreshToken.getBaekjoonId();
        if(refreshTokenRepository.existsByBaekjoonId(loginUserBaekjoonId)){
            refreshTokenRepository.deleteByBaekjoonId(loginUserBaekjoonId);
        }
        refreshTokenRepository.save(refreshToken);
    }
    @Override
    public ValidateTokenResponseDto validateRefreshToken(String refreshToken) {
        RefreshToken refreshToken1 = getRefreshToken(refreshToken);
        String createdAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken1);

        return new ValidateTokenResponseDto(createdAccessToken);
    }
    @Override
    public RefreshToken getRefreshToken(String refreshToken) {
        RefreshToken existRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken).
        orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        return existRefreshToken;
    }
}

