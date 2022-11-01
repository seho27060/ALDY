package com.example.demo.domain.dto;

import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.MemberInStudy;
import com.example.demo.domain.entity.Study;
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

    private String backjoonId;

    public MemberInStudyDto(MemberInStudy memberInStudy) {
        this.id = memberInStudy.getId();
        this.auth = memberInStudy.getAuth();
        this.message = memberInStudy.getMessage();
        this.studyId = memberInStudy.getStudy().getId();
        this.backjoonId = memberInStudy.getMember().getBackjoonId();
    }

}
