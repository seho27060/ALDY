package com.example.demo.domain.dto.study;

import com.example.demo.domain.dto.study.StudyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyPageResponseDto {

    private int numberOfPages;

    private long numberOfObject;

    private Page<StudyDto> studyDtoPage;

    public StudyPageResponseDto(int numberOfPages, Page<StudyDto> studyDtoPage) {
        this.numberOfPages = numberOfPages;
        this.studyDtoPage = studyDtoPage;
    }

}
