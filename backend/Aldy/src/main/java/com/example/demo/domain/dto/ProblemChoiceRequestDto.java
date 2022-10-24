package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemChoiceRequestDto {
    private long study_id;
    private List<Integer> problemList;
    private int year;
    private int month;
    private int day;
}
