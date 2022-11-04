package com.example.demo.domain.dto.solvedac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDto {

    private int problemId;
    private String titleKo;
    private int level;
    private int averageTries;
    private int acceptedUserCount;

}
