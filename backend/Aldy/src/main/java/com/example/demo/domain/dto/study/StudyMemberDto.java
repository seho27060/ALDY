package com.example.demo.domain.dto.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyMemberDto {
    private long memberId;
    private String baekjoonId;
    private String nickname;
    private int process;
}
