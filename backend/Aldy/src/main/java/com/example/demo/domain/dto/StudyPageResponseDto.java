package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
public class StudyPageResponseDto {

    private int numberOfPages;

    private Page<StudyDto> studyDtoPage;

    public StudyPageResponseDto(int numberOfPages, Page<StudyDto> studyDtoPage) {
        this.numberOfPages = numberOfPages;
        this.studyDtoPage = studyDtoPage;
    }

}
