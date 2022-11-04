package com.example.demo.domain.dto.solvedac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagsDisplayNamesDto {
    private String language;
    private String name;
}
