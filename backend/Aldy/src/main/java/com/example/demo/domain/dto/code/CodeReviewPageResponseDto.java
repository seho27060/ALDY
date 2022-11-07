package com.example.demo.domain.dto.code;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CodeReviewPageResponseDto {
    private List<EditedCodeDto> editingCodeList;
    private List<EditedCodeDto> editedCodeList;
    private List<RequestedCodeDto> requestingCodeList;
    private List<RequestedCodeDto> requestedCodeList;
    public CodeReviewPageResponseDto(List<EditedCodeDto> editingCodeList, List<EditedCodeDto> editedCodeList, List<RequestedCodeDto> requestingCodeList, List<RequestedCodeDto> requestedCodeList) {
        this.editedCodeList = editedCodeList;
        this.editingCodeList = editingCodeList;
        this.requestedCodeList = requestedCodeList;
        this.requestingCodeList = requestingCodeList;
    }
}
