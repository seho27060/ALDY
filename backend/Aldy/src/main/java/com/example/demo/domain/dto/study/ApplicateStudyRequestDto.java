package com.example.demo.domain.dto.study;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApplicateStudyRequestDto {

    @Schema(description = "스터디 Id")
    private Long studyId;

    @Schema(description = "가입 신청 메세지")
    private String message;

}
