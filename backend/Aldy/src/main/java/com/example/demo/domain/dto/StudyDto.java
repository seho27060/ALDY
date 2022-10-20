package com.example.demo.domain.dto;

import com.example.demo.domain.entity.Study;
import com.example.demo.service.MemberInStudyService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyDto {

    private Long id;
    private LocalDateTime createdDate;
    private String name;
    private int upperLimit;
    private String introduction;
    private int threshold;
    private int visibility;
    private int countMember;

    public StudyDto(Study study) {
        this.id = study.getId();
        this.createdDate = study.getCreatedDate();
        this.name = study.getName();
        this.upperLimit = study.getUpperLimit();
        this.introduction = study.getIntroduction();
        this.threshold = study.getThreshold();
        this.visibility = study.getVisibility();
    }

    public StudyDto(Study study, int countMember) {
        this.id = study.getId();
        this.createdDate = study.getCreatedDate();
        this.name = study.getName();
        this.upperLimit = study.getUpperLimit();
        this.introduction = study.getIntroduction();
        this.threshold = study.getThreshold();
        this.visibility = study.getVisibility();
    }
}
