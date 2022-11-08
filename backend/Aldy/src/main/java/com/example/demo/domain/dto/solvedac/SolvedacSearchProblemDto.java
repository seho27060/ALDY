package com.example.demo.domain.dto.solvedac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolvedacSearchProblemDto {

    private Long count;
    private List<ProblemWithTagDisplayNamesVo> items;

}
