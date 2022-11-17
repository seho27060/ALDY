package com.example.demo.domain.dto.study;

import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.Study.Study;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyDetailResponseDto {

    private Long id;
    private LocalDateTime createdDate;
    private String name;
    private int upperLimit;
    private String introduction;
    private int threshold;
    private int visibility;
    private int countMember;

    private String leaderBaekjoonId;
    private String leaderEmail;
    private HashMap<Integer, Integer> statsByTier;
    private HashMap<String, Integer> statsByTag;

    private Boolean isMember;
    private Boolean isKick;

    private int level;
    private int activationLevel;

    public StudyDetailResponseDto(Study study) {
        this.id = study.getId();
        this.createdDate = study.getCreatedDate();
        this.name = study.getName();
        this.upperLimit = study.getUpperLimit();
        this.introduction = study.getIntroduction();
        this.threshold = study.getThreshold();
        this.visibility = study.getVisibility();
        this.level = study.getLevel();
        this.activationLevel = study.getActivationLevel();
    }

    public void setLeaderInfo(Member member) {
        this.leaderBaekjoonId = member.getBaekjoonId();
        this.leaderEmail = member.getEmail();
    }

}
