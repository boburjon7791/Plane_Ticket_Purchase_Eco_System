package com.example.demo.services;

import com.example.demo.dto.AuthUserDto;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AdminService {
    void setRoleAdmin(String name);
    void removeRoleAdmin(String name);
    void setRoleAgent(String name);
    void removeRoleAgent(String name);

    void block(@NonNull String email);

    void unblock(@NonNull String email);
}
