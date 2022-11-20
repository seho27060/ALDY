package com.example.demo.domain.dto.member.response;

import com.example.demo.domain.entity.Member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;

    private String baekjoonId;
    private String nickname;
    private String email;
    private Long tier;

    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.baekjoonId = member.getBaekjoonId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.tier = member.getTier();
    }
}
