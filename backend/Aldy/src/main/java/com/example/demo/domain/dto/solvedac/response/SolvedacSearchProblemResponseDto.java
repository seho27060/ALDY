package com.example.demo.domain.dto.solvedac.response;

import com.example.demo.domain.dto.ProblemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SolvedacSearchProblemResponseDto {
    private Long count;
    private ProblemDto problemDto;
}
