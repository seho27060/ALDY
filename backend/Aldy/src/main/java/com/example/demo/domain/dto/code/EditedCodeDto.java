package com.example.demo.domain.dto.code;

import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Code.EditedCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class EditedCodeDto {
    private Long id;

    private LocalDateTime editedDate;
    private MemberResponseDto sender;
    private MemberResponseDto receiver;
    private CodeDto codeDto;

    private String editedCode;

    public EditedCodeDto(EditedCode editedCode){
        this.id = editedCode.getId();
        this.editedCode = editedCode.getText();
        this.codeDto = new CodeDto(editedCode.getCode());
        this.sender = new MemberResponseDto(editedCode.getSender());
        this.receiver = new MemberResponseDto(editedCode.getReceiver());
        this.editedDate = editedCode.getEditedDate();
    }
}
