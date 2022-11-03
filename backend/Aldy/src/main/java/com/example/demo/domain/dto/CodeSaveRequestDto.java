package com.example.demo.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeSaveRequestDto {

//    private String backjoon_id;
    private String code;
    private int process;
    private long studyId;
    private long problemId;
    private String problemName;
    private int problemTier;
}
