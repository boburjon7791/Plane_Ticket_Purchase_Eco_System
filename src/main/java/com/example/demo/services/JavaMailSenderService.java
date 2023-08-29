package com.example.demo.services;

import com.example.demo.entities.ActivateCodes;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class JavaMailSenderService {
    public final JavaMailSender javaMailSender;

    @Autowired
    public JavaMailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void send(@NonNull ActivateCodes activateCodes,@NonNull String text) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setText(text+activateCodes.getCode());
        InternetAddress address = new InternetAddress();
        address.setAddress(activateCodes.getAuthUser().getEmail());
        mimeMessage.addRecipient(Message.RecipientType.TO,address);
        javaMailSender.send(mimeMessage);
    }
}
