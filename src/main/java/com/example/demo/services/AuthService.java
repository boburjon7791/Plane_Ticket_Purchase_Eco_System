package com.example.demo.services;

import com.example.demo.dto.AuthUserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthUserDto register(@NonNull AuthUserDto authUserDto, HttpServletResponse res, HttpServletRequest req);
    void activate(@NonNull Integer code, @NonNull String email,
                  HttpServletRequest req, HttpServletResponse res);
    void generateAgainActivationCode(@NonNull String email, HttpServletRequest req, HttpServletResponse res);
}
