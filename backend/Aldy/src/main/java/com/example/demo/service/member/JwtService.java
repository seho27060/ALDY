package com.example.demo.service.member;

import com.example.demo.domain.dto.member.Token;
import com.example.demo.domain.entity.Member.RefreshToken;

import java.util.Map;

public interface JwtService {
    void login(Token tokenDto);
    RefreshToken getRefreshToken(String refreshToken);
    Map<String, String> createRefreshJson(String createdAccessToken);
    Map<String,String> validateRefreshToken(String refreshToken);

}
