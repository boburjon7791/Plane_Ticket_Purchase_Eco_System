package com.example.demo.services;

import com.example.demo.dto.AuthUserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    AuthUserDto updateAuthUser(AuthUserDto authUserDto);
}
