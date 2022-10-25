package com.example.demo.domain.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String password;
    private String nickname;
    private String contact;

}
