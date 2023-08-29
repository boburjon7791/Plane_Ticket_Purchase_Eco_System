package com.example.demo.services;

import com.example.demo.dto.CompanyDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CompanyService {
    CompanyDto companyCreate(CompanyDto companyDto);
    CompanyDto companyEdit(CompanyDto companyDto);
    CompanyDto companyGet(String name);
    void companyDelete(String name);
    List<CompanyDto> getAllCompanies();
}
