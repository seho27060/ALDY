package com.example.demo.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CodeReviewRequestDto {
//    private String sender_id;
    private String receiver_id;
    private long study_id;
    private long problem_id;
}
