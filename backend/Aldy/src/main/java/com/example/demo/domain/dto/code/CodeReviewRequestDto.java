package com.example.demo.domain.dto.code;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CodeReviewRequestDto {
//    private String sender_id;
    private List<String> receiverId;
    private long studyId;
    private long problemId;
}
