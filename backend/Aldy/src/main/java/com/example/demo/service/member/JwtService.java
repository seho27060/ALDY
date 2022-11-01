package com.example.demo.service.member;

import com.example.demo.domain.dto.member.response.TokenDto;
import com.example.demo.domain.dto.member.response.ValidateTokenResponseDto;
import com.example.demo.domain.entity.Member.RefreshToken;

import java.util.Map;

public interface JwtService {
    void login(TokenDto tokenDto);
    RefreshToken getRefreshToken(String refreshToken);
    ValidateTokenResponseDto validateRefreshToken(String refreshToken);

}
