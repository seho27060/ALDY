package com.example.demo.domain.entity.Member;

import com.example.demo.domain.dto.member.request.MemberJoinRequestDto;
import com.example.demo.domain.entity.EditedCode;
import com.example.demo.domain.entity.MemberInStudy;
import com.example.demo.domain.entity.RequestedCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String backjoonId;

    private String password;

    // 연략처 전화번호 or 이메일
    private String contact;

    private String nickname;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<MemberInStudy> studyList;

    @OneToMany(mappedBy = "sender",cascade = CascadeType.ALL)
    private List<EditedCode> sendEditedCodeList;
    @OneToMany(mappedBy = "receiver",cascade = CascadeType.ALL)
    private List<EditedCode> receiveEditedCodeList;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<RequestedCode> seedRequestedCodeList;
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<RequestedCode> receiveRequestedCodeList;

    public Member(MemberJoinRequestDto memberJoinRequestDto, String encodedPassword){
        this.backjoonId = memberJoinRequestDto.getBackjoonId();
        this.nickname = memberJoinRequestDto.getNickname();
        this.password = encodedPassword;
        this.contact = memberJoinRequestDto.getContact();
    }
}
