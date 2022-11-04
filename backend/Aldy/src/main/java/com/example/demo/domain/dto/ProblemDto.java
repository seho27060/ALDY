package com.example.demo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemDto {

    private int problemId;
    private String titleKo;
    private int acceptedUserCount;
    private int level;

}
