package com.example.demo.service;

import com.example.demo.domain.dto.MailDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private String title_type_1 = "알디 코드 리뷰 요청 메일 알림";
    private String title_type_2 = "알디 코드 리뷰 응답 메일 알림";
    private String title_type_3 = "알디 스터디 강퇴 알림 메일";
    private String template_1 ="안녕하세요, 알고리즘 코드 리뷰 서비스 알디입니다.\n" +
            "이 메일은 알디에서 자동으로 발송된 이메일입니다.\n" +
            "%s 님께서 보내신 코드 리뷰 요청이 도착했습니다. 일주일 안에 코드 리뷰 응답해주시길 바랍니다.";

    private String template_2 ="안녕하세요, 알고리즘 코드 리뷰 서비스 알디입니다.\n" +
            "이 메일은 알디에서 자동으로 발송된 이메일입니다.\n" +
            "%s 님께서 보내신 코드 리뷰가 도착했습니다. 실력 향상에 도움이 되길 바랍니다.";
    private String template_3 ="안녕하세요, 알고리즘 코드 리뷰 서비스 알디입니다.\n" +
            "이 메일은 알디에서 자동으로 발송된 이메일입니다.\n" +
            "%s 님께서 속하신 스터디 %s 에서 3회 코드 리뷰 무응답으로 인해 추방되었음을 알려드립니다.";



    public void sendSimpleMessage(MailDto mailDto) {
        String type = mailDto.getType();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("aldyssafy502@gmail.com");
        String title = "";
        String text = "";
        message.setTo(mailDto.getAddress());

        switch(type) {
            case "reply":
                title = title_type_1;
                text = String.format(template_1, mailDto.getNickname());
                break;
            case "request":
                title = title_type_2;
                text = String.format(template_2, mailDto.getNickname());
                break;
            case "evict":
                title = title_type_3;
                text = String.format(template_3, mailDto.getNickname(), mailDto.getStudyName());
                break;
        }
        System.out.println(text);
        message.setSubject(title);
        message.setText(text);
        emailSender.send(message);
        }
    }
