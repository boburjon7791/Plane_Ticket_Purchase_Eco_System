package com.example.demo.controllers;

import com.example.demo.dto.CompanyDto;
import com.example.demo.dtoRequest.CompanyDtoR;
import com.example.demo.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.company")
@PreAuthorize("hasRole('ADMIN')")
public class CompanyController {
    public final CompanyService companyService;
    @PostMapping("/create")
    public ResponseEntity<UUID> createCompany(@RequestBody @Valid CompanyDtoR companyDtoR){
        CompanyDtoR created = companyService.companyCreate(companyDtoR);
        return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
    }
    @GetMapping("/get/all")
    @PreAuthorize("isAuthenticated()")
    public Page<CompanyDtoR> getAll(@RequestParam Map<String,String> param){
        int page= Integer.parseInt(param.getOrDefault("page","1"));
        int size= Integer.parseInt(param.getOrDefault("size","5"));
        return companyService.getAllCompanies(page, size);
    }
    @GetMapping("/get/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CompanyDtoR> getCompany(@PathVariable String name){
        CompanyDtoR companyDto = companyService.companyGet(name);
        return new ResponseEntity<>(companyDto, HttpStatus.OK);
    }

    @GetMapping("/get-id/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CompanyDtoR> getCompanyId(@PathVariable String id){
        CompanyDtoR companyDto = companyService.companyGet(UUID.fromString(id));
        return new ResponseEntity<>(companyDto, HttpStatus.OK);
    }

    @PutMapping("/update/{name}")
    @Transactional
    public ResponseEntity<UUID> updateCompany(@PathVariable String name,
                                              @RequestBody @Valid CompanyDtoR companyDtoR){
        companyDtoR.setName(name);
        CompanyDtoR edited = companyService.companyEdit(companyDtoR);
        return new ResponseEntity<>(edited.getId(),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{name}")
    @Transactional
    public void deleteCompany(@PathVariable String name){
        companyService.companyDelete(name);
    }
}
