package com.example.demo.domain.dto.code;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CodeResponseDto {

    private int currentProcess;
    private CodeDto firstProcessCode;
    private CodeDto secondProcessCode;
    private CodeDto thirdProcessCode;
//    private CodeDto fourthProcessCode;
    public CodeResponseDto(List<CodeDto> codeDtoList){

        currentProcess = 0;

        for(CodeDto code : codeDtoList){
            if(code.getProcess() == 1){
                firstProcessCode = code;
                currentProcess++;
            }
            else if(code.getProcess() == 2){
                secondProcessCode = code;
                currentProcess++;
            }
//            else if(code.getProcess() == 3){
//                thirdProcessCode = code;
//                currentProcess++;
//            }
            else{
                thirdProcessCode = code;
//              fourthProcessCode = code;
                currentProcess++;
            }
        }
    }

}
