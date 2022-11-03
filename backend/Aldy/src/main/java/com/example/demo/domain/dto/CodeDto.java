package com.example.demo.domain.dto;

import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Code;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CodeDto {
    private Long id;

    private LocalDateTime createdDate;

    private MemberResponseDto writer;

    private StudyDto studyDto;

    private String code;

    private int level;

    private Long problemId;

    private String problemName;

    private int problemTier;

    public CodeDto(Code code){
        this.code= code.getCode();
        this.createdDate = code.getCreatedDate();
        this.writer = new MemberResponseDto(code.getWriter());
        this.studyDto = new StudyDto(code.getStudy(),0);
        this.id = code.getId();
        this.level = code.getProcess();
        this.problemId = code.getProblemId();
        this.problemName = code.getProblemName();
        this.problemTier = code.getProblemTier();
    }
}
