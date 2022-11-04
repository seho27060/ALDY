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

    private long numberOfObject;

    private Page<StudyDto> studyDtoPage;

    public StudyPageResponseDto(int numberOfPages, long numberOfObject, Page<StudyDto> studyDtoPage) {
        this.numberOfPages = numberOfPages;
        this.numberOfObject = numberOfObject;
        this.studyDtoPage = studyDtoPage;
    }

}
