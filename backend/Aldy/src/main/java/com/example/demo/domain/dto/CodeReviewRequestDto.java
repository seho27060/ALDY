package com.example.demo.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CodeReviewRequestDto {
//    private String sender_id;
    private String receiverId;
    private long studyId;
    private long problemId;
}
