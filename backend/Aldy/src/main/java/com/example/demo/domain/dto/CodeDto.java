package com.example.demo.domain.dto;

import com.example.demo.domain.entity.Code;
import com.example.demo.domain.entity.Member;
import com.example.demo.domain.entity.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CodeDto implements Comparable<CodeDto>{
    private Long id;

    private LocalDateTime createdDate;

    private String code;

    private int process;

    private Long problemId;

    private String problemName;

    private int problemTier;

    public CodeDto(Code code){
        this.code= code.getCode();
        this.createdDate = code.getCreatedDate();
        this.id = code.getId();
        this.process = code.getProcess();
        this.problemId = code.getProblemId();
        this.problemName = code.getProblemName();
        this.problemTier = code.getProblemTier();
    }

    @Override
    public int compareTo(CodeDto codeDto){
        return this.process - codeDto.getProcess();
    }
}
