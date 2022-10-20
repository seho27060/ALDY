package com.example.demo.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeSaveRequestDto {

    private long backjoon_id;
    private String code;
    private int process;
    private long study_id;
    private long problem_id;
    private String problem_name;
    private int problem_tier;
}
