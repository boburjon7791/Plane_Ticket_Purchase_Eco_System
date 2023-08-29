package com.example.demo.services;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.entities.Auditable;
import com.example.demo.entities.AuthUser;
import com.example.demo.repositories.AuthUserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    Auditable register(@NonNull AuthUserDto authUserDto);
    void activate(@NonNull Integer code, @NonNull String email, HttpServletResponse res);
    void generateAgainActivationCode(@NonNull String email);
}
