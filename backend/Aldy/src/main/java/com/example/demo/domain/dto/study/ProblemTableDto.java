package com.example.demo.domain.dto.study;

import com.example.demo.domain.entity.Study.Problem;

public class ProblemTableDto {
    private Long id;

    private CalendarDto calendar;

    private int problemId;
    private int problemTier;
    private String problemName;

    private int problemDay;
    public ProblemTableDto(Problem problem) {
    }
}
