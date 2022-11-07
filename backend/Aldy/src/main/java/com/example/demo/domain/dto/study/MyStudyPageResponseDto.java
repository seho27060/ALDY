package com.example.demo.domain.dto.study;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
public class MyStudyPageResponseDto {

    private int numberOfPages;

    private long numberOfObject;

    private Page<MyStudyDto> myStudyDtoPage;

    public MyStudyPageResponseDto(int numberOfPages, long numberOfObject, Page<MyStudyDto> myStudyDtoPage) {
        this.numberOfPages = numberOfPages;
        this.numberOfObject = numberOfObject;
        this.myStudyDtoPage = myStudyDtoPage;
    }

}
