package com.example.demo.domain.dto.study;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberInStudyChangeAuthDto {

    private Long memberId;

    private Long studyId;

}
