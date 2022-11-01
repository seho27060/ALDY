package com.example.demo.domain.dto.member.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {
    private String backjoonId;
    private String nickname;
    private String password;
    private String contact;
}
