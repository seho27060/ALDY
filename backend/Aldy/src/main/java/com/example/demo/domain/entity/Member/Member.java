package com.example.demo.domain.entity.Member;

import com.example.demo.domain.dto.member.request.MemberModifyEmailRequestDto;
import com.example.demo.domain.dto.member.request.MemberModifyNicknameRequestDto;
import com.example.demo.domain.entity.Code.EditedCode;
import com.example.demo.domain.entity.Study.MemberInStudy;
import com.example.demo.domain.entity.Code.RequestedCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    @Column(length = 50)
    private String baekjoonId;

    private String password;
    // 연략처 전화번호 or 이메일
    private String email;

    private String nickname;

    private Long tier;

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

    public void modifyNickname(MemberModifyNicknameRequestDto memberModifyNicknameRequestDto){
        this.nickname = memberModifyNicknameRequestDto.getNickname();
    }
    public void modifyEmail(MemberModifyEmailRequestDto memberModifyEmailRequestDto){
        this.email = memberModifyEmailRequestDto.getEmail();
    }
    public void modifyPassword(String password){
        this.password = password;
    }
    public void renewTier(Long tier){
        this.tier = tier;
    }
}

