package com.example.demo.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void setRoleAdmin(String name);
    void removeRoleAdmin(String name);
    void setRoleAgent(String name, String companyId);
    void removeRoleAgent(String name);

    void block(@NonNull String email);

    void unblock(@NonNull String email);
}
