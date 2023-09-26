package com.example.demo.services;

import com.example.demo.entities.ActivateCodes;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JavaMailSenderService {
    public final JavaMailSender javaMailSender;

    @Async
    @SneakyThrows
    public void send(@NonNull ActivateCodes activateCodes,@NonNull String text) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setText(text+activateCodes.getCode());
            InternetAddress address = new InternetAddress();
            address.setAddress(activateCodes.getAuthUser().getEmail());
            mimeMessage.addRecipient(Message.RecipientType.TO,address);
            javaMailSender.send(mimeMessage);
            log.info("send message to {}",activateCodes.getAuthUser().getEmail());
    }
    @Async
    @SneakyThrows
    public void send(@NonNull String email,@NonNull String text){
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setText(text);
            InternetAddress address = new InternetAddress();
            address.setAddress(email);
            mimeMessage.addRecipient(Message.RecipientType.TO,address);
            javaMailSender.send(mimeMessage);
            log.info("message send to {}",email);
    }
}
