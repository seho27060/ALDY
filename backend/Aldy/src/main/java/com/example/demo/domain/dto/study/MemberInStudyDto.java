package com.example.demo.domain.dto.study;

import com.example.demo.domain.entity.Study.MemberInStudy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInStudyDto {

    private Long id;
    private int auth;
    private String message;
    private Long studyId;
    private String baekjoonId;
    private String nickname;
    private Long tier;
    private Integer solvedTogether;

    public MemberInStudyDto(MemberInStudy memberInStudy) {
        this.id = memberInStudy.getId();
        this.auth = memberInStudy.getAuth();
        this.message = memberInStudy.getMessage();
        this.studyId = memberInStudy.getStudy().getId();
        this.baekjoonId = memberInStudy.getMember().getBaekjoonId();
        this.nickname = memberInStudy.getMember().getNickname();
        this.tier = memberInStudy.getMember().getTier();
    }

    public MemberInStudyDto(MemberInStudy memberInStudy, int solvedTogether) {
        this.id = memberInStudy.getId();
        this.auth = memberInStudy.getAuth();
        this.message = memberInStudy.getMessage();
        this.studyId = memberInStudy.getStudy().getId();
        this.baekjoonId = memberInStudy.getMember().getBaekjoonId();
        this.nickname = memberInStudy.getMember().getNickname();
        this.tier = memberInStudy.getMember().getTier();
        this.solvedTogether = solvedTogether;
    }

}
