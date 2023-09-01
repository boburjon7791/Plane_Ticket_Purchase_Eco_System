package com.example.demo.services;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.dtoRequest.AuthUserDtoR;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    AuthUserDtoR updateAuthUser(AuthUserDtoR authUserDtoR);
}
