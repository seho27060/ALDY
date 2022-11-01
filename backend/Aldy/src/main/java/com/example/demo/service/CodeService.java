package com.example.demo.service;

import com.example.demo.domain.dto.*;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

// edited, request 모두 여기서 처리할 것.
public interface CodeService {
    CodeResponseDto getCodesByStudy_idAndProblem_idAndMember_id(long study_id, long problem_id, HttpServletRequest request);

    CodeReviewPageResponseDto getCodesByMember_id(HttpServletRequest request);

    EditedCodeDto replyEditedCode(CodeReviewReplyDto codeReviewReplyDto, HttpServletRequest request);

    CodeDto saveCode(CodeSaveRequestDto codeSaveRequestDto, HttpServletRequest request);

    RequestedCodeDto requestCode(CodeReviewRequestDto codeReviewRequestDto, HttpServletRequest request);
}
