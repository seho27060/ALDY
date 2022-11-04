package com.example.demo.domain.dto.code;

import com.example.demo.domain.dto.study.ProblemTableDto;
import com.example.demo.domain.dto.study.StudyDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Code.Code;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CodeDto {
    private Long id;

    private LocalDateTime createdDate;

    private MemberResponseDto writer;

    private StudyDto studyDto;

    private String code;

    private int process;

    private ProblemTableDto problem;

//    private Long problemId;

//    private String problemName;

//    private int problemTier;

    public CodeDto(Code code){
        this.code= code.getCode();
        this.createdDate = code.getCreatedDate();
        this.writer = new MemberResponseDto(code.getWriter());
        this.studyDto = new StudyDto(code.getStudy(),0);
        this.id = code.getId();
        this.process = code.getProcess();
        this.problem = new ProblemTableDto(code.getProblemTable());
//        this.problemId = code.getProblemId();
//        this.problemName = code.getProblemName();
//        this.problemTier = code.getProblemTier();
    }
}
