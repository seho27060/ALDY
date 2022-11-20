package com.example.demo.domain.dto.member.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterlockResponseDto {
    private boolean isInterlock;
    public InterlockResponseDto(boolean isInterlock){
        this.isInterlock = isInterlock;
    }
}
