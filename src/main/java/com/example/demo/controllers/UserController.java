package com.example.demo.controllers;

import com.example.demo.dtoRequest.AuthUserDtoR;
import com.example.demo.exceptions.ForbiddenAccessException;
import com.example.demo.services.UserService;
import com.example.demo.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @CachePut(key = "#authUserDtoR.id", value = "authUsers")
    public ResponseEntity<AuthUserDtoR> editUser(HttpServletRequest request, @RequestBody @Valid AuthUserDtoR authUserDtoR){
        String email = JwtTokenUtil.getEmail(request);
        if (!authUserDtoR.getEmail().equals(email)) {
            throw new ForbiddenAccessException();
        }
        AuthUserDtoR updated = userService.updateAuthUser(authUserDtoR);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
