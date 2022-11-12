package com.example.demo.domain.entity.Code;

import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.Study.Problem;
import com.example.demo.domain.entity.Study.Study;
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

    @Column(length = 10000)
    private String code;

    private int process;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="problem_id")
    private Problem problem;

    public void updateCode(String code){
        this.code = code;
    }
//    public Code(CodeSaveRequestDto codeSaveRequestDto, Member writer){
//        this.code = codeSaveRequestDto.getCode();
//        this.writer = writer;
//        this.process = codeSaveRequestDto.getProcess();
//        this.problemId = codeSaveRequestDto.getProblemId();
//        this.problemName = codeSaveRequestDto.getProblemName();
//        this.problemTier = codeSaveRequestDto.getProblemTier();
//    }

}
