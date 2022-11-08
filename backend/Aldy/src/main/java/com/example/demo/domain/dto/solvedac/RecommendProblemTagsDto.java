package com.example.demo.domain.dto.solvedac;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RecommendProblemTagsDto extends ProblemTagsDto{
    List<TagsDisplayNames> displayNames;
}
