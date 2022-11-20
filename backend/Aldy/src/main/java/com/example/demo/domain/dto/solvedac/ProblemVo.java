package com.example.demo.domain.dto.solvedac;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProblemVo {
  private int problemId;
  private String titleKo;
  private int level;
  private int averageTries;
  private int acceptedUserCount;
}
