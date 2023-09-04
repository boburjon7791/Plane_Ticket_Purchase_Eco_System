package com.example.demo.controllers;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.dtoRequest.AuthUserDtoR;
import com.example.demo.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api.auth")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class AuthController {
    public final AuthService authService;
    @PostMapping("/register")
    public void register(@RequestBody @Valid AuthUserDtoR authUserDtor,
                                                HttpServletResponse res,
                                                HttpServletRequest req){
        authService.register(authUserDtor,res,req);
    }
    @PostMapping("/login")
    public String login(@RequestParam Map<String,String> param, HttpServletResponse res){
        String email = param.get("email");
        String password = param.get("password");
        return authService.login(email,password,res);
    }

    @PostMapping("/activate/{code}")
    public void activate(@PathVariable Integer code,@RequestParam Map<String,String> param,
                         HttpServletRequest req, HttpServletResponse res){
        String email = param.get("email");
        authService.activate(code,email,req,res);
    }
    @PostMapping("/again-sent-code/{email}")
    public void sendAgain(@PathVariable String email,
                          HttpServletRequest req,
                          HttpServletResponse res){
        authService.generateAgainActivationCode(email,req,res);
    }
}
