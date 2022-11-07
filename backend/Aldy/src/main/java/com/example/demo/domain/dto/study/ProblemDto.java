package com.example.demo.domain.dto.study;

import com.example.demo.domain.entity.Study.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemDto {

    private Long id;
    private int problemNum;

    private int problemDay;

    private int problemTier;

    private String problemName;

//    private List<CodeDto> codeList;


    public ProblemDto(Problem problem) {
        this.id = problem.getId();
        this.problemNum = problem.getProblemNum();
        this.problemDay = problem.getProblemDay();
        this.problemTier = problem.getProblemTier();
        this.problemName = problem.getProblemName();
//        this.codeList = problem.getCodeList().stream().map(CodeDto::new).collect(Collectors.toList());
    }
}
