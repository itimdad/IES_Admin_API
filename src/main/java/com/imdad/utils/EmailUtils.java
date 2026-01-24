package com.imdad.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class EmailUtils {

    private final JavaMailSender javaMailSender;

    EmailUtils(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public boolean sendMail(String subject, String to, String body) throws Exception {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper  helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);;
        helper.setText(body, true);

        javaMailSender.send(mimeMessage);

        return true;
    }
}
