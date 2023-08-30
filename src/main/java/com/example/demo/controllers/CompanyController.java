package com.example.demo.controllers;

import com.example.demo.dto.CompanyDto;
import com.example.demo.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.company")
@PreAuthorize("hasRole('ADMIN')")
public class CompanyController {
    public final CompanyService companyService;
    @PostMapping("/create")
    public ResponseEntity<CompanyDto> createCompany(@RequestBody @Valid CompanyDto companyDto){
        CompanyDto created = companyService.companyCreate(companyDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    @GetMapping("/get/all")
    @PreAuthorize("isAuthenticated()")
    public Page<CompanyDto> getAll(@RequestParam Map<String,String> param){
        int page= Integer.parseInt(param.getOrDefault("page","0"));
        int size= Integer.parseInt(param.getOrDefault("size","5"));
        return companyService.getAllCompanies(page, size);
    }
    @GetMapping("/get/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable String name){
        CompanyDto companyDto = companyService.companyGet(name);
        return new ResponseEntity<>(companyDto, HttpStatus.OK);
    }
    @PutMapping("/update/{name}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable String name,
                                                    @RequestBody @Valid CompanyDto companyDto){
        companyDto.setName(name);
        CompanyDto edited = companyService.companyEdit(companyDto);
        return new ResponseEntity<>(edited,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{name}")
    public void deleteCompany(@PathVariable String name){
        companyService.companyDelete(name);
    }
}
