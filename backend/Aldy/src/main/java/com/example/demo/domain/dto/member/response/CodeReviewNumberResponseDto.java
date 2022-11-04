package com.example.demo.domain.dto.member.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CodeReviewNumberResponseDto {
    private Long answerCodeReviewNumber;
    private Long replyCodeReviewNumber;

    public CodeReviewNumberResponseDto(Long answerReviewNumber, Long requerstReviewNumber){
        this.answerCodeReviewNumber = answerReviewNumber;
        this.replyCodeReviewNumber = requerstReviewNumber;
    }
}
