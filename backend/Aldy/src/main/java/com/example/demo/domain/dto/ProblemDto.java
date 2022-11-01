package com.example.demo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ProblemDto {

    private int num;

    private String name;

    private int acceptedUserCount;

    public ProblemDto(int num, String name, int acceptedUserCount) {
        this.num = num;
        this.name = name;
        this.acceptedUserCount = acceptedUserCount;
    }
}
