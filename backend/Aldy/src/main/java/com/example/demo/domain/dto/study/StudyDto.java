package com.example.demo.domain.dto.study;

import com.example.demo.domain.entity.Study.Study;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
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

    public StudyDto(Study study, int countMember) {
        this.id = study.getId();
        this.createdDate = study.getCreatedDate();
        this.name = study.getName();
        this.upperLimit = study.getUpperLimit();
        this.introduction = study.getIntroduction();
        this.threshold = study.getThreshold();
        this.visibility = study.getVisibility();
        this.countMember = countMember;
    }

}
