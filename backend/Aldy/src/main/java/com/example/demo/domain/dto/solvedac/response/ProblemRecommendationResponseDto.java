package com.example.demo.domain.dto.solvedac.response;

import com.example.demo.domain.dto.solvedac.ProblemVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProblemRecommendationResponseDto extends ProblemVo {
    private String algorithm;
}
