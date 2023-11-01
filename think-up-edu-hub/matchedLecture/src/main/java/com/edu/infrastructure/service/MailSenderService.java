package com.edu.infrastructure.service;

import com.edu.domain.dto.EmailDetailItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(EmailDetailItem emailDetailItem){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);		// 보내는 이메일
        message.setTo("tjddn9984@gmail.com");	// 받는 이메일
        message.setSubject(emailDetailItem.getTitle());		// 메일 제목
        message.setText(String.format("안녕하세요. %s님 수강신청 하신 %s에 대해  %s원이 입금 되지 않아 이메일을 보냅니다."
                        ,emailDetailItem.getName()
                        ,emailDetailItem.getTitle()
                        ,emailDetailItem.getPrice())
                );		// 메일 내용
        message.setSentDate(new Date());	// 송신 날짜
        mailSender.send(message);		// 실제 보내기
    }

}
