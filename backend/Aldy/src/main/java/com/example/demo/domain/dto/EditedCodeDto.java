package com.example.demo.domain.dto;

import com.example.demo.domain.entity.EditedCode;
import com.example.demo.domain.entity.RequestedCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EditedCodeDto {
    private Long id;

    //    private MemberDto sender;
//    private MemberDto receiver;
    private CodeDto codeDto;
    public EditedCodeDto(EditedCode editedCode){
        this.id = editedCode.getId();
        this.codeDto = new CodeDto(editedCode.getCode());
//        this.sender = new MemberDto(requestedCode.getSender());
//        this.receiver = new MemberDto(requestedCode.getReceiver());
    }
}
