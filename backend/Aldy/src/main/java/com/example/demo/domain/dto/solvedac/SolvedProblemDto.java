package com.example.demo.domain.dto.solvedac;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SolvedProblemDto extends com.example.demo.domain.dto.solvedac.ProblemVo {
    private List<ProblemTagsDto> tags;
}
