package com.example.demo.domain.entity;

import com.example.demo.domain.dto.CodeSaveRequestDto;
import com.example.demo.domain.entity.Member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Code {

//     골프공 == 서비스 구현체
//     홀 == 컴프넌트
//    내가 홀이라는 개념을 몰라.
//     홀 안에 골프공 넣으래



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @CreatedDate
    private LocalDateTime createdDate;

    private String code;

    private int process;

    private Long problemId;

    @Column(length = 50)
    private String problemName;

    private int problemTier;

    public Code(CodeSaveRequestDto codeSaveRequestDto, Member writer){
        this.code = codeSaveRequestDto.getCode();
        this.writer = writer;
        this.process = codeSaveRequestDto.getProcess();
        this.problemId = codeSaveRequestDto.getProblem_id();
        this.problemName = codeSaveRequestDto.getProblem_name();
        this.problemTier = codeSaveRequestDto.getProblem_tier();
    }

}
