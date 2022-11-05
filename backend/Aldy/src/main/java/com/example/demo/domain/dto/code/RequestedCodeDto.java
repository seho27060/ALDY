package com.example.demo.domain.dto.code;


import com.example.demo.domain.dto.code.CodeDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Code.RequestedCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 독립적 테이블이 별로 없음.
// 9개 API 분류
// 멤버 API, 내가 7개 상현이 1개
// 결과적으로는 API 만져봐야 됨.
// 일부러 헨젤과 그레텔마냥 코드 손볼 데를 남겨 놓는 중
// 시큐리티를 최대한 빠르게 해결하느냐 마느냐
// 아는 사람에게 물어보는 방법도 빠를 수 있음.
// 컨트, 하현서 코치


@Getter
@NoArgsConstructor
public class RequestedCodeDto {
    private Long id;
    private boolean isDone;

    private LocalDateTime requestDate;
    private MemberResponseDto sender;
    private MemberResponseDto receiver;
    private CodeDto codeDto;
    public RequestedCodeDto(RequestedCode requestedCode){
        this.id = requestedCode.getId();
        this.codeDto = new CodeDto(requestedCode.getCode());
        this.isDone = requestedCode.getIsDone();
        this.sender = new MemberResponseDto(requestedCode.getSender());
        this.receiver = new MemberResponseDto(requestedCode.getReceiver());
        this.requestDate = requestedCode.getRequestDate();
    }
}
