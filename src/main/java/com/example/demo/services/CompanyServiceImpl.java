package com.example.demo.services;

import com.example.demo.dto.CompanyDto;
import com.example.demo.entities.Company;
import com.example.demo.mappers.CompanyMapper;
import com.example.demo.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    public final CompanyRepository companyRepository;
    public final CompanyMapper companyMapper = CompanyMapper.COMPANY_MAPPER;
    @Override
    public CompanyDto companyCreate(CompanyDto companyDto) {
        try {
            Company company = companyMapper.toEntity(companyDto);
            Company save = companyRepository.save(company);
            return companyMapper.toDto(save);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
            return null;
        }
    }

    @Override
    public CompanyDto companyEdit(CompanyDto companyDto) {
        try {
            Company company = companyMapper.toEntity(companyDto);
            Company save = companyRepository.save(company);
            return companyMapper.toDto(save);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
            return null;
        }
    }

    @Override
    public CompanyDto companyGet(String name) {
        try {
            Company company = companyRepository.findByName(name);
            return companyMapper.toDto(company);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
            return null;
        }
    }

    @Override
    public void companyDelete(String name) {
        try {
            companyRepository.deleteByName(name);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
        }
    }

    @Override
    public Page<CompanyDto> getAllCompanies(int page,int size) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Company> all = companyRepository.findAll(pageable);
            return companyMapper.toDtoPage(all);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
            return null;
        }
    }
}
