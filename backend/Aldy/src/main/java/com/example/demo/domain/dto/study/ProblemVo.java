package com.example.demo.domain.dto.study;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemVo {

    private int problemId;
    private String titleKo;
    private int acceptedUserCount;
    private int level;

}
