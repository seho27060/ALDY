package com.example.demo.domain.dto.solvedac;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProblemWithTagDisplayNamesVo extends ProblemVo{
    private List<TagsWithDisplayNamesDto> tags;
}
