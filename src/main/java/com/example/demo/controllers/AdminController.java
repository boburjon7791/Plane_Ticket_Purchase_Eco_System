package com.example.demo.controllers;

import com.example.demo.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.admin")
public class AdminController {
    public final AdminService adminService;
}
