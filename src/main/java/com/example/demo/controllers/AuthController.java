package com.example.demo.controllers;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.auth")
@RequiredArgsConstructor
public class AuthController {
    public final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthUserDto> register(@RequestBody AuthUserDto authUserDto,
                                                HttpServletResponse res,
                                                HttpServletRequest req){
        AuthUserDto register = authService.register(authUserDto,res,req);
        return ResponseEntity.ok(register);
    }
    @PostMapping("/activate/{code}")
    public void activate(@PathVariable Integer code,@CookieValue(name = "email") String email,
                         HttpServletRequest req, HttpServletResponse res){
        authService.activate(code,email,req,res);
    }
    @PostMapping("/again-sent-code")
    public void sendAgain(@CookieValue(name = "email") String email,
                          HttpServletRequest req,
                          HttpServletResponse res){
        authService.generateAgainActivationCode(email,req,res);
    }
}
