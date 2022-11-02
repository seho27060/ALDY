package com.example.demo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MailDto {
    private String address;
//    request, reply, evict
    private String type;
    private String receiver_nickname;
    private String sender_nickname;
    private String StudyName;
}
