package com.example.demo.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String address;
//    request, reply, evict
    private String type;
    private String nickname;
    private String StudyName;
}
