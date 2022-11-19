package com.example.demo.service.study;

import com.example.demo.domain.dto.study.MailDto;
import com.example.demo.domain.entity.Study.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender emailSender;
    private String title_type_1 = "알디 코드 리뷰 요청 메일 알림";
    private String title_type_2 = "알디 코드 리뷰 응답 메일 알림";
    private String title_type_3 = "알디 스터디 강퇴 알림 메일";
    private String title_type_4 = "알디 스터디원 신청 알림 메일";
    private String title_type_5 = "알디 스터디 가입 신청 완료 알림 메일";
    private String template_1 ="안녕하세요, %s 님. 알고리즘 코드 리뷰 서비스 알디입니다.\n" +
            "이 메일은 알디에서 자동으로 발송된 이메일입니다.\n" +
            "%s 스터디에서 %s 님이 보내신 코드 리뷰 요청이 도착했습니다. 일주일 안에 코드 리뷰에 응답해주시길 바랍니다.";

    private String template_2 ="안녕하세요, %s 님. 알고리즘 코드 리뷰 서비스 알디입니다.\n" +
            "이 메일은 알디에서 자동으로 발송된 이메일입니다.\n" +
            "%s 스터디에서 %s 님이 보내신 코드 리뷰가 도착했습니다. 실력 향상에 도움이 되길 바랍니다.";
    private String template_3 ="안녕하세요, %s 님. 알고리즘 코드 리뷰 서비스 알디입니다.\n" +
            "이 메일은 알디에서 자동으로 발송된 이메일입니다.\n" +
            "%s 님께서 속하신 %s 스터디에서 3회 코드 리뷰 무응답으로 인해 추방되었음을 알려드립니다.";

    private String template_4 ="안녕하세요, %s 스터디장 님. 알고리즘 코드 리뷰 서비스 알디입니다.\n" +
            "이 메일은 알디에서 자동으로 발송된 이메일입니다.\n" +
            "%s 님께서 %s 스터디에 스터디원 가입 요청이 왔음을 알려드립니다.";

    private String template_5 ="안녕하세요, %s 님. 알고리즘 코드 리뷰 서비스 알디입니다.\n"+
            "이 메일은 알디에서 자동으로 발송된 이메일입니다.\n" +
            "%s 님께서 가입 신청하신 %s 스터디에 가입이 완료되었음을 알려드립니다.";
//    메일 발송 핵심 기능을 맡고 있는 함수
    public void sendSimpleMessage(MailDto mailDto) {
        String type = mailDto.getType();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("aldyssafy502@gmail.com");
        String title = "";
        String text = "";
        message.setTo(mailDto.getAddress());
        switch(type) {
            case "request":
                title = title_type_1;
                text = String.format(template_1, mailDto.getReceiverNickname(),
                        mailDto.getStudyName(),
                        mailDto.getSenderNickname());
                break;
            case "reply":
                title = title_type_2;
                text = String.format(template_2, mailDto.getReceiverNickname(),
                        mailDto.getStudyName(),
                        mailDto.getSenderNickname());
                break;
            case "evict":
                title = title_type_3;
                text = String.format(template_3, mailDto.getReceiverNickname(),
                        mailDto.getReceiverNickname(),
                        mailDto.getStudyName());
                break;
            case "apply":
                title = title_type_4;
                text = String.format(template_4, mailDto.getStudyName(),
                        mailDto.getReceiverNickname(),
                        mailDto.getStudyName());
                break;
            case "approve":
                title = title_type_5;
                text = String.format(template_5, mailDto.getReceiverNickname(),
                        mailDto.getReceiverNickname(),
                        mailDto.getStudyName());
                break;
        }

        message.setSubject(title);
        message.setText(text);
        emailSender.send(message);
        }
        // 실제 서비스단에서 코드 리뷰 요청, 응답 시 메일 발송 함수
    @Async
    public void sendCodeAlertEmail(Study study, String mail_address, String sender_nickname, String receiver_nickname, String type){
        MailDto mailDto = MailDto.builder()
            .studyName(study.getName())
            .address(mail_address)
            .receiverNickname(receiver_nickname)
            .senderNickname(sender_nickname)
            .type(type)
            .build();
        sendSimpleMessage(mailDto);
    }

    // 실제 서비스단에서 강제 퇴장 시 사용되는 메일 발송 함수
    @Async
    public void sendEvictionMail(Study study, String mail_address, String receiver_nickname, String type){
        MailDto mailDto = MailDto.builder()
                .studyName(study.getName())
                .address(mail_address)
                .receiverNickname(receiver_nickname)
                .type(type)
                .build();
        sendSimpleMessage(mailDto);
    }

    @Async
    public void sendApplicationMail(Study study, String mail_address, String receiver_nickname, String type){
        MailDto mailDto = MailDto.builder()
                .studyName(study.getName())
                .address(mail_address)
                .receiverNickname(receiver_nickname)
                .type(type)
                .build();
        sendSimpleMessage(mailDto);
    }
    @Async
    public void sendApproveMail(Study study, String mail_address, String receiver_nickname, String type){
        MailDto mailDto = MailDto.builder()
                .studyName(study.getName())
                .address(mail_address)
                .receiverNickname(receiver_nickname)
                .type(type)
                .build();
        sendSimpleMessage(mailDto);
    }
}
