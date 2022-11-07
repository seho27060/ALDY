package com.example.demo.domain.dto.study;

import com.example.demo.domain.entity.Study.Study;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
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
    private int countMember;
    private int numberOfSolvedProblem;
    private int tierOfRecentSolvedProblem;

    public MyStudyDto(Study study, int countMember, int numberOfSolvedProblem, int tierOfRecentSolvedProblem) {
        this.id = study.getId();
        this.createdDate = study.getCreatedDate();
        this.name = study.getName();
        this.upperLimit = study.getUpperLimit();
        this.introduction = study.getIntroduction();
        this.threshold = study.getThreshold();
        this.countMember = countMember;
        this.numberOfSolvedProblem = numberOfSolvedProblem;
        this.tierOfRecentSolvedProblem = tierOfRecentSolvedProblem;
    }

}

