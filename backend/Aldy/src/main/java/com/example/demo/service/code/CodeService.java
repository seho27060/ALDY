package com.example.demo.service.code;

import com.example.demo.domain.dto.code.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

// edited, request 모두 여기서 처리할 것.
public interface CodeService {
    CodeResponseDto getCodesByStudy_idAndProblem_idAndMember_id(long study_id, long problem_id, HttpServletRequest request);

    CodeReviewPageResponseDto getCodesByMember_id(HttpServletRequest request);

    EditedCodeDto replyEditedCode(CodeReviewReplyDto codeReviewReplyDto, HttpServletRequest request);

    CodeDto saveCode(CodeSaveRequestDto codeSaveRequestDto, HttpServletRequest request);

    RequestedCodeDto requestCode(CodeReviewRequestDto codeReviewRequestDto, HttpServletRequest request);

    List<EditedCodeDto> getEditedCodes(long studyId, int problemId, HttpServletRequest request);
}