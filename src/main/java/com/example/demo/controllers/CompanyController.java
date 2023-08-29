package com.example.demo.controllers;

import com.example.demo.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.company")
public class CompanyController {
    public final CompanyService companyService;
}
