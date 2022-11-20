package com.example.demo.domain.dto.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CodeReviewDto {
    private String baeckjoonId;
    private String code;
    private String receiverId;
    private long problemId;
    private long studyId;
}
