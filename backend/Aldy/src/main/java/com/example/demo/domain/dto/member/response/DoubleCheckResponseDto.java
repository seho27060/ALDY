package com.example.demo.domain.dto.member.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoubleCheckResponseDto {
    private boolean doubleCheck;
    public DoubleCheckResponseDto(boolean doubleCheck){
        this.doubleCheck = doubleCheck;
    }
}
