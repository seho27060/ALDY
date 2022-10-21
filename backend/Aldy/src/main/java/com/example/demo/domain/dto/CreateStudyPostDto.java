package com.example.demo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudyPostDto {

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

    @Schema(description = "스터디 방장 백준 아이디")
    private String leaderBackjoonId;

}
