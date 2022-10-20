package com.example.demo.service;

import com.example.demo.domain.dto.*;
import org.springframework.http.ResponseEntity;

// edited, request 모두 여기서 처리할 것.
public interface CodeService {
    CodeResponseDto getCodesByStudy_idAndProblem_idAndMember_id(long study_id, long problem_id, long member_id);

    CodeReviewResponseDto getCodesByMember_id(long member_id);

    EditedCodeDto replyEditedCode(CodeReviewRequestDto codeReviewRequestDto);

    CodeDto saveCode(CodeSaveRequestDto codeSaveRequestDto);

    RequestedCodeDto requestCode(CodeSaveRequestDto codeSaveRequestDto);
}
