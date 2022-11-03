package com.example.demo.domain.dto.member.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidateTokenRequestDto {
    private String refreshToken;
    public ValidateTokenRequestDto(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
