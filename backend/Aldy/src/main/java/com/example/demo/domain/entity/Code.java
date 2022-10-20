package com.example.demo.domain.entity;

import com.example.demo.domain.dto.CodeSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreatedDate
    private LocalDateTime createdDate;

    private String code;

    private int process;

    private Long problemId;

    @Column(length = 50)
    private String problemName;

    private int problemTier;

    public Code(CodeSaveRequestDto codeSaveRequestDto){
        this.code = codeSaveRequestDto.getCode();
        this.process = codeSaveRequestDto.getProcess();
        this.problemId = codeSaveRequestDto.getProblem_id();
        this.problemName = codeSaveRequestDto.getProblem_name();
        this.problemTier = codeSaveRequestDto.getProblem_tier();
    }
}
