package com.example.demo.domain.dto;


import com.example.demo.domain.entity.RequestedCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestedCodeDto {
    private Long id;

//    private MemberDto sender;
//    private MemberDto receiver;
    private CodeDto codeDto;
    public RequestedCodeDto(RequestedCode requestedCode){
        this.id = requestedCode.getId();
        this.codeDto = new CodeDto(requestedCode.getCode());
//        this.sender = new MemberDto(requestedCode.getSender());
//        this.receiver = new MemberDto(requestedCode.getReceiver());
    }
}
