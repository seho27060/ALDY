package com.example.demo.domain.dto.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StudyStatusDto {
    List<StudyMemberDto> studyMemberDtoList;
}
