package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CodeReviewReplyDto {
    private String backjoon_id;
    private String code;
    private String receiver_id;
    private long problem_id;
    private long study_id;
}
