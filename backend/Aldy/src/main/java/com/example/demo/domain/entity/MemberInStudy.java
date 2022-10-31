package com.example.demo.domain.entity;

import com.example.demo.domain.entity.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MemberInStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int auth;

    // null 허용
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberInStudy(Study study, Member member, int auth) {
        this.study = study;
        this.member = member;
        this.auth = auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }
}
