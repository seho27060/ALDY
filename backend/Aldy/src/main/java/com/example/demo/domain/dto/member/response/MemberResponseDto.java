package com.example.demo.domain.dto.member.response;

import com.example.demo.domain.entity.Member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private String backjoonId;
    private String nickname;
    private String contact;

    public MemberResponseDto(Member member){
        this.backjoonId = member.getBackjoonId();
        this.contact = member.getContact();
        this.nickname = member.getNickname();
    }
}
