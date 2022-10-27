package com.example.demo.domain.dto.member.response;

import com.example.demo.domain.entity.Member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String backjoonId;
    private String contact;

    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.backjoonId = member.getBackjoonId();
        this.contact = member.getContact();
    }
}
