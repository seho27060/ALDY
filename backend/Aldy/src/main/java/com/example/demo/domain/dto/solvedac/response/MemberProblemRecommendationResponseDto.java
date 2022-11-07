package com.example.demo.domain.dto.solvedac.response;

import com.example.demo.domain.dto.study.ProblemVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MemberProblemRecommendationResponseDto extends ProblemVo {
    private String algorithm;
}
