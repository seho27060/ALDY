package com.example.demo.domain.dto.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CodeNumberRelatedMemberResponseDto {
    private int editedCodeNumber;
    private int requestCodeNumber;

    public CodeNumberRelatedMemberResponseDto(int editedCodeNumber, int requestCodeNumber){
        this.editedCodeNumber = editedCodeNumber;
        this.requestCodeNumber = requestCodeNumber;
    }
}
