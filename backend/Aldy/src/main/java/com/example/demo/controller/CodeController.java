package com.example.demo.controller;

import com.example.demo.domain.dto.*;
import com.example.demo.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @GetMapping("/{study_id}/{problem_id}")
    private ResponseEntity code01(@PathVariable long study_id, @PathVariable long problem_id, HttpRequest request){
//      원래는 리퀘스트로부터 토큰값 없고 멤버 아이디 식별
        long member_id = 3;

        CodeResponseDto codeResponseDto = codeService.getCodesByStudy_idAndProblem_idAndMember_id(
                study_id, problem_id, member_id
        );
        return new ResponseEntity(codeResponseDto,HttpStatus.OK);
    }

    @GetMapping("review-page")
    private ResponseEntity code02(HttpRequest request){
//      원래는 리퀘스트로부터 토큰값에서 멤버 아이디 식별
        long member_id = 4;

        CodeReviewResponseDto codeReviewResponseDto = codeService.getCodesByMember_id(member_id);
        return new ResponseEntity(codeReviewResponseDto, HttpStatus.OK);
    }
    @PostMapping("/reply")
    private ResponseEntity code03(HttpRequest request, @RequestBody CodeReviewRequestDto codeReviewRequestDto){
        EditedCodeDto editedCodeDto = codeService.replyEditedCode(codeReviewRequestDto);
        return new ResponseEntity(editedCodeDto, HttpStatus.OK);
    }

    @PostMapping("/process")
    private ResponseEntity code04(HttpRequest request, @RequestBody CodeSaveRequestDto codeSaveRequestDto){
        // 원래는.. ㅇㅇ
        long member_id = 3;

        CodeDto codeDto = codeService.saveCode(codeSaveRequestDto);
        return new ResponseEntity(codeDto, HttpStatus.OK);
    }

    @PostMapping("/request")
    private ResponseEntity code05(HttpRequest request, @RequestBody CodeSaveRequestDto codeSaveRequestDto){
        // 원래는.. ㅇㅇ
        long member_id = 3;

        CodeDto codeDto = codeService.saveCode(codeSaveRequestDto);
        RequestedCodeDto requestedCodeDto = codeService.requestCode(codeSaveRequestDto);


        return new ResponseEntity(requestedCodeDto, HttpStatus.OK);
    }
}
