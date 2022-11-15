package com.example.demo.domain.dto.study;

import com.example.demo.domain.entity.Study.Study;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyStudyDto {

    private Long id;
    private LocalDateTime createdDate;
    private String name;
    private int upperLimit;
    private String introduction;
    private int threshold;
    private int visibility;
    private int countMember;
    private int numberOfSolvedProblem;
    private int tierOfRecentSolvedProblem;

    public MyStudyDto(Study study, int countMember) {
        this.id = study.getId();
        this.createdDate = study.getCreatedDate();
        this.name = study.getName();
        this.upperLimit = study.getUpperLimit();
        this.introduction = study.getIntroduction();
        this.threshold = study.getThreshold();
        this.countMember = countMember;
        this.visibility = study.getVisibility();
    }

}

