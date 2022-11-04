package com.example.demo.domain.entity.Code;

import com.example.demo.domain.dto.code.CodeSaveRequestDto;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.Study.ProblemTable;
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

    private ProblemTable problemTable;

//    public Code(CodeSaveRequestDto codeSaveRequestDto, Member writer){
//        this.code = codeSaveRequestDto.getCode();
//        this.writer = writer;
//        this.process = codeSaveRequestDto.getProcess();
//        this.problemId = codeSaveRequestDto.getProblemId();
//        this.problemName = codeSaveRequestDto.getProblemName();
//        this.problemTier = codeSaveRequestDto.getProblemTier();
//    }

}
