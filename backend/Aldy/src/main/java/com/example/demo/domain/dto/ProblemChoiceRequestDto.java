package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemChoiceRequestDto {
    private long studyId;
    private List<Integer> problemList;
    private int year;
    private int month;
    private int day;
}
