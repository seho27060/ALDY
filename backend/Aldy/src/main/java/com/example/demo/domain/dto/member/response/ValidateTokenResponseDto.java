package com.example.demo.domain.dto.member.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidateTokenResponseDto {
    private String accessToken;
    public ValidateTokenResponseDto(String accessToken){
        this.accessToken = accessToken;
    }
}
