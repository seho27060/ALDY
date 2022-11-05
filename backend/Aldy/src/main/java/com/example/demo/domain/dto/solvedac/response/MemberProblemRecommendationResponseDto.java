package com.example.demo.domain.dto.solvedac.response;

import com.example.demo.domain.dto.solvedac.ProblemVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
