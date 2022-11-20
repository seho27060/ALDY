package com.example.demo.domain.dto.study;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MailDto {
    private String address;
//    request, reply, evict
    private String type;
    private String receiverNickname;
    private String senderNickname;
    private String studyName;
}
