package com.example.demo.domain.dto.code;

import com.example.demo.domain.dto.study.StudyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinalCodeResponseDto {

    private int numberOfPages;

    private long numberOfObject;

    private Page<CodeDto> codeDtoPage;


}