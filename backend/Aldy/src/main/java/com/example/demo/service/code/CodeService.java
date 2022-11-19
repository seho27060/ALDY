package com.example.demo.service.code;

import com.example.demo.domain.dto.code.*;
import com.example.demo.domain.dto.study.ProblemDto;
import com.example.demo.domain.dto.study.StudyStatusDto;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

// edited, request 모두 여기서 처리할 것.
public interface CodeService {
    CodeResponseDto getCodesByStudy_idAndProblem_idAndMember_id(long study_id, long problem_id, HttpServletRequest request);

    CodeReviewPageResponseDto getCodesByMember_id(HttpServletRequest request);

    EditedCodeDto replyEditedCode(CodeReviewReplyDto codeReviewReplyDto, HttpServletRequest request);

    CodeDto saveCode(CodeSaveRequestDto codeSaveRequestDto, HttpServletRequest request);

    List<RequestedCodeDto> requestCode(CodeReviewRequestDto codeReviewRequestDto, HttpServletRequest request);

    List<EditedCodeDto> getEditedCodes(long studyId, int problemId, HttpServletRequest request);

    Page<RequestedCodeDto> getMyRequestedCodeList(int page, int size, String loginMember);
    Page<RequestedCodeDto> getMyRequestingCodeList(int page, int size, String loginMember);
    Page<EditedCodeDto> getMyEditedCodeList(int page, int size, String loginMember);
    Page<EditedCodeDto> getMyEditingCodeList(int page, int size, String loginMember);

    StudyStatusDto getProcessOfStudy(long study_id, long problem_id, HttpServletRequest request);

    List<ProblemDto> getProblemsOfDay(long study_id, int year, int month, int day, HttpServletRequest request);

    Page<CodeDto> getMyFinalCodeList(int page, int size, String loginMember);
}
