package com.example.demo.controllers;

import com.example.demo.dtoRequest.AuthUserDtoR;
import com.example.demo.services.AdminService;
import com.example.demo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api.admin")
public class AdminController {
    public final AdminService adminService;
    private final AuthService authService;


    @Transactional
    @PutMapping("/block-user/{email}")
    public void block(@PathVariable String email){
        adminService.block(email);
    }
    @Transactional
    @PutMapping("/unblock-user/{email}")
    public void unblock(@PathVariable String email){
        adminService.unblock(email);
    }
    @Transactional
    @PutMapping("/set-admin/{email}")
    public void setAdmin(@PathVariable String email){
        adminService.setRoleAdmin(email);
    }

    @Transactional
    @PutMapping("/remove-admin/{email}")
    public void removeAdmin(@PathVariable String email){
        adminService.removeRoleAdmin(email);
    }

    @Transactional
    @PutMapping("/set-agent/{email}")
    public void setAgent(@PathVariable String email, @RequestParam String companyId){
        adminService.setRoleAgent(email,companyId);
    }
    @GetMapping("/get-user/{id}")
    public ResponseEntity<AuthUserDtoR> get(@PathVariable String id){
        AuthUserDtoR authUserDtoR = authService.get(UUID.fromString(id));
        return ResponseEntity.ok(authUserDtoR);
    }

    @Transactional
    @PutMapping("/remove-agent/{email}")
    public void removeAgent(@PathVariable String email){
        adminService.removeRoleAgent(email);
    }
}
