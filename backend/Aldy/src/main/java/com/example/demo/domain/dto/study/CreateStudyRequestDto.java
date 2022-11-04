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
public class CreateStudyRequestDto {

    @Schema(description = "스터디 이름")
    private String name;

    @Schema(description = "스터디 제한인원")
    private int upperLimit;

    @Schema(description = "스터디 설명")
    private String introduction;

    @Schema(description = "스터디 가입요건")
    private int threshold;

    @Schema(description = "스터디 공개범위")
    private int visibility;

}
