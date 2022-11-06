package com.example.demo.domain.dto.solvedac;

import com.example.demo.domain.dto.study.ProblemVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SolvedProblemDto extends ProblemVo {
    private List<ProblemTagsDto> tags;
}
