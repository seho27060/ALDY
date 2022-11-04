package com.example.demo.controller;

import com.example.demo.domain.dto.*;
import com.example.demo.domain.dto.code.*;
import com.example.demo.service.CodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
@Tag(name = "Code API - 담당자 조성민", description = "코드 관련 API")
public class CodeController {

    private final CodeService codeService;

    @GetMapping("/{study_id}/{problem_id}")
    @Operation(summary = "코드 반환 API", description = "사용자의 단계별 코드 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    private ResponseEntity code01(@PathVariable long study_id, @PathVariable long problem_id, HttpServletRequest request){

        CodeResponseDto codeResponseDto = codeService.getCodesByStudy_idAndProblem_idAndMember_id(
                study_id, problem_id, request
        );
        return new ResponseEntity(codeResponseDto,HttpStatus.OK);
    }

    @GetMapping("review-page")
    @Operation(summary = "코드 리뷰 요청 리스트 반환 API", description = "요청 받은 거, 보낸 거, 첨삭 받은 거 리스트 반환해줌")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    private ResponseEntity code02(HttpServletRequest request){

        CodeReviewPageResponseDto codeReviewPageResponseDto = codeService.getCodesByMember_id(request);
        return new ResponseEntity(codeReviewPageResponseDto, HttpStatus.OK);
    }
    @PostMapping("/reply")
    @Operation(summary = "코드 리뷰 첨삭 API", description = "코드 리뷰한 것을 돌려주는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    private ResponseEntity code03(HttpServletRequest request, @RequestBody CodeReviewReplyDto codeReviewReplyDto){
        EditedCodeDto editedCodeDto = codeService.replyEditedCode(codeReviewReplyDto, request);
        return new ResponseEntity(editedCodeDto, HttpStatus.OK);
    }

    @PostMapping("/process")
    @Operation(summary = "코드 저장 API", description = "각 단계의 코드를 DB에 저장한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    private ResponseEntity code04(HttpServletRequest request, @RequestBody CodeSaveRequestDto codeSaveRequestDto){

        CodeDto codeDto = codeService.saveCode(codeSaveRequestDto, request);
        return new ResponseEntity(codeDto, HttpStatus.OK);
    }

    @PostMapping("/request")
    @Operation(summary = "코드 리뷰 요청 API", description = "실제로 체크된 사람에게 코드 리뷰 요청을 보낸다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "실패"),
    })
    private ResponseEntity code05(HttpServletRequest request, @RequestBody CodeReviewRequestDto codeReviewRequestDto){

        RequestedCodeDto requestedCodeDto = codeService.requestCode(codeReviewRequestDto, request);
        return new ResponseEntity(requestedCodeDto, HttpStatus.OK);
    }

    @GetMapping("/getEditedCodes/{studyId}/{problemId}")
    @Operation(summary = "코드 리뷰 첨삭 모음 API", description = "이 스터디에서 특정 문제에 대해 내가 첨삭받은 코드들을 반환해준다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "실패"),
    })
    private ResponseEntity code06(HttpServletRequest request, @PathVariable long studyId, @PathVariable int problemId){
        List<EditedCodeDto> requestedCodeDtoList = codeService.getEditedCodes(studyId, problemId, request);
        return new ResponseEntity(requestedCodeDtoList, HttpStatus.OK);
    }
}
