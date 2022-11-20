package com.example.demo.domain.entity.Study;

import com.example.demo.domain.entity.Member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberInStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 0 : 강퇴, 1 : Leader, 2 : 스터디원, 3 : 가입 신청, 4 : 탈퇴
    private int auth;

    // null 허용
    private String message;

    private int numberOfAlerts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberInStudy(Study study, Member member, int auth, String message) {
        this.study = study;
        this.member = member;
        this.auth = auth;
        this.numberOfAlerts = 0;
        this.message = message;
    }

}
