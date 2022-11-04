package com.example.demo.domain.dto;

import com.example.demo.domain.dto.solvedac.ProblemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemFilterDto {

    private int count;

    private List<ProblemDto> items;

}
