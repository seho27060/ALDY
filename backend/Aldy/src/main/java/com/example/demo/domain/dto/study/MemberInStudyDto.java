package com.example.demo.domain.dto.study;

import com.example.demo.domain.entity.Study.MemberInStudy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInStudyDto {

    @Schema(description = "MemberInStudyId")
    private Long id;
    private int auth;
    private String message;
    @Schema(description = "StudyId")
    private Long studyId;
    @Schema(description = "MemberId")
    private Long memberId;

    @Schema(description = "Member Baekjoon Id")
    private String baekjoonId;
    private String nickname;
    private Long tier;
    @Schema(description = "함께 푼 문제")
    private Integer solvedTogether;
    private Integer numberOfAlerts;

    public MemberInStudyDto(MemberInStudy memberInStudy) {
        this.id = memberInStudy.getId();
        this.auth = memberInStudy.getAuth();
        this.message = memberInStudy.getMessage();
        this.studyId = memberInStudy.getStudy().getId();
        this.memberId = memberInStudy.getMember().getId();
        this.baekjoonId = memberInStudy.getMember().getBaekjoonId();
        this.nickname = memberInStudy.getMember().getNickname();
        this.tier = memberInStudy.getMember().getTier();
        this.numberOfAlerts = memberInStudy.getNumberOfAlerts();
    }

    public MemberInStudyDto(MemberInStudy memberInStudy, int solvedTogether) {
        this.id = memberInStudy.getId();
        this.auth = memberInStudy.getAuth();
        this.message = memberInStudy.getMessage();
        this.studyId = memberInStudy.getStudy().getId();
        this.memberId = memberInStudy.getMember().getId();
        this.baekjoonId = memberInStudy.getMember().getBaekjoonId();
        this.nickname = memberInStudy.getMember().getNickname();
        this.tier = memberInStudy.getMember().getTier();
        this.solvedTogether = solvedTogether;
        this.numberOfAlerts = memberInStudy.getNumberOfAlerts();
    }

}
