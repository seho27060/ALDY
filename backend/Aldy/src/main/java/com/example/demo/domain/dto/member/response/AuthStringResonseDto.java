package com.example.demo.domain.dto.member.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthStringResonseDto {
    private String authString;

    public AuthStringResonseDto(String authString){
        this.authString = authString;
    }
}
