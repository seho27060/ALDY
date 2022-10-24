package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApplicateStudyRequestDto {

    private Long memberId;

    private Long studyId;

    private String message;

}
