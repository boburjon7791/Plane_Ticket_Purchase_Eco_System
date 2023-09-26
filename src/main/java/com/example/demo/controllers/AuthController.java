package com.example.demo.controllers;

import com.example.demo.dtoRequest.AuthUserDtoR;
import com.example.demo.exceptions.ForbiddenAccessException;
import com.example.demo.services.AuthService;
import com.example.demo.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("/{id}")
    @Cacheable(key = "#id",value = "authUsers")
    public ResponseEntity<AuthUserDtoR> getInfo(HttpServletRequest request,@PathVariable String id){
        AuthUserDtoR body = authService.get(UUID.fromString(id));
        String email = JwtTokenUtil.getEmail(request);
        if (email==null) {
            return null;
        }
        if (!email.equals(body.getEmail())) {
            throw new ForbiddenAccessException();
        }
        return ResponseEntity.ok(body);
    }
}
