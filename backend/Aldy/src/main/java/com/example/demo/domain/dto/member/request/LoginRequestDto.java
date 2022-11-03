package com.example.demo.domain.dto.member.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    private String baekjoonId;
    private String password;
}
