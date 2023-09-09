package com.example.demo.services;

import com.example.demo.dtoRequest.AuthUserDtoR;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AuthService {
    void register(@NonNull AuthUserDtoR authUserDtoR, HttpServletResponse res, HttpServletRequest req);
    void activate(@NonNull Integer code, @NonNull String email,
                  HttpServletRequest req, HttpServletResponse res);
    void generateAgainActivationCode(@NonNull String email, HttpServletRequest req, HttpServletResponse res);

    String login(String email, String password, HttpServletResponse res);

    AuthUserDtoR get(UUID id);
}
