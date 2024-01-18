package com.example.demo.controllers;

import com.example.demo.dtoRequest.AuthUserDtoR;
import com.example.demo.exceptions.ForbiddenAccessException;
import com.example.demo.services.AuthService;
import com.example.demo.services.CustomUserDetails;
import com.example.demo.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

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
    public ResponseEntity<AuthUserDtoR> login(@RequestParam Map<String,String> param, HttpServletResponse res){
        String email = param.get("email");
        String password = param.get("password");
        AuthUserDtoR login = authService.login(email, password, res);
        return ResponseEntity.ok(login);
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
    @GetMapping("/get")
    public ResponseEntity<AuthUserDtoR> getInfo(){
        CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUserDtoR body = authService.get(userDetails.authUser().getId());
        return ResponseEntity.ok(body);
    }
}
