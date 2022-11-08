package com.example.demo.domain.dto.solvedac;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemVo {

    private int problemId;
    private String titleKo;
    private int level;
    private int averageTries;
    private int acceptedUserCount;

    private List<ProblemTagsDto> tags;

}