package com.example.demo.domain.dto.solvedac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/*
"tags": [
      {
        "key": "implementation",
        "isMeta": false,
        "bojTagId": 102,
        "problemCount": 3745,
        "displayNames": [
          {
            "language": "ko",
            "name": "구현",
            "short": "구현"
          },
          {
            "language": "en",
            "name": "implementation",
            "short": "impl"
          }
        ]
      },
      {
        "key": "arithmetic",
        "isMeta": false,
        "bojTagId": 121,
        "problemCount": 677,
        "displayNames": [
          {
            "language": "ko",
            "name": "사칙연산",
            "short": "사칙연산"
          },
          {
            "language": "en",
            "name": "arithmetic",
            "short": "arithmetic"
          }
        ]
      },
      {
        "key": "math",
        "isMeta": false,
        "bojTagId": 124,
        "problemCount": 4445,
        "displayNames": [
          {
            "language": "ko",
            "name": "수학",
            "short": "수학"
          },
          {
            "language": "en",
            "name": "mathematics",
            "short": "math"
          }
        ]
      }
    ]
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemTagsDto {
    private String key;
    private List<TagsDisplayNamesDto> displayNames;
}
