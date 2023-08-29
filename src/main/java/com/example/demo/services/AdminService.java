package com.example.demo.services;

import com.example.demo.dto.AuthUserDto;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void setRoleAdmin(String name);
    void removeRoleAdmin(String name);
    void setRoleAgent(String name);
    void removeRoleAgent(String name);
}
