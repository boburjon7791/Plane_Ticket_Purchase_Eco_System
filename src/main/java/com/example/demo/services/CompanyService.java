package com.example.demo.services;

import com.example.demo.dtoRequest.CompanyDtoR;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CompanyService {
    CompanyDtoR companyCreate(CompanyDtoR companyDtoR);
    CompanyDtoR companyEdit(CompanyDtoR companyDtoR);
    CompanyDtoR companyGet(String name);
    Page<CompanyDtoR> getAllCompanies(int size,int limit);

    CompanyDtoR companyGet(UUID id);
}
