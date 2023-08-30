package com.example.demo.services;

import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {
    public final JavaMailSenderService javaMailSenderService;
    @Override
    public void sendReportEditFlight(Flight flight, LocalDateTime oldTime) {
        String message= """
                Hurmatli mijoz %s dagi parvoz
                %s ga ko'chirilganini ma'lum qilmoqchimiz.
                Keltirilgan noqulayliklar uchun uzr so'raymiz.
                """.formatted(oldTime.toString(),flight.getLocalDateTime().toString());
        Set<AuthUser> authUsers = flight.getAuthUsers();
        Runnable runnable = () -> authUsers.forEach(authUser -> javaMailSenderService.send(authUser.getEmail(),message));
        runnable.run();
    }
}
