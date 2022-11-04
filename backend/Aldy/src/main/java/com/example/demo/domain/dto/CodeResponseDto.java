package com.example.demo.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CodeResponseDto {
    private CodeDto firstLevelCode;
    private CodeDto secondLevelCode;
    private CodeDto thirdLevelCode;
    private CodeDto fourthLevelCode;
    public CodeResponseDto(List<CodeDto> codeDtoList){
        for(CodeDto code : codeDtoList){
            if(code.getProcess() == 1)
                firstLevelCode = code;
            else if(code.getProcess() == 2)
                secondLevelCode = code;
            else if(code.getProcess() == 3)
                thirdLevelCode = code;
            else
                fourthLevelCode = code;
        }
    }

}
