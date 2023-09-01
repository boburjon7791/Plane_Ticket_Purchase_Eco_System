package com.example.demo.controllers;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.dtoRequest.AuthUserDtoR;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.user")
public class UserController {
    public final UserService userService;
    @PutMapping("/edit/{email}")
    @Transactional
    public ResponseEntity<AuthUserDtoR> editUser(@RequestBody @Valid AuthUserDtoR authUserDtoR){
        AuthUserDtoR updated = userService.updateAuthUser(authUserDtoR);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
