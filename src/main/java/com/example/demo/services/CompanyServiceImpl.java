package com.example.demo.services;

import com.example.demo.dto.CompanyDto;
import com.example.demo.entities.Company;
import com.example.demo.mappers.CompanyMapper;
import com.example.demo.repositories.CompanyRepository;
import io.swagger.v3.oas.models.links.Link;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;


@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    public final CompanyRepository companyRepository;
    public final CompanyMapper companyMapper;
    @Override
    public CompanyDto companyCreate(CompanyDto companyDto) {
        try {
            Company company = companyMapper.toEntity(companyDto);
            Company save = companyRepository.save(company);
            CompanyDto dto = companyMapper.toDto(save);
            log.info("{} created",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public CompanyDto companyEdit(CompanyDto companyDto) {
        try {
            Company company = companyMapper.toEntity(companyDto);
            Company save = companyRepository.save(company);
            CompanyDto dto = companyMapper.toDto(save);
            log.info("{} updated",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public CompanyDto companyGet(String name) {
        try {
            Company company = companyRepository.findByName(name);
            CompanyDto dto = companyMapper.toDto(company);
            log.info("{} gave",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public void companyDelete(String name) {
        try {
            companyRepository.deleteByName(name);
            log.info("{} company deleted",name);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public Page<CompanyDto> getAllCompanies(int page,int size) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Company> all = companyRepository.findAll(pageable);
            all.getContent().forEach(System.out::println);
            if (all.getContent().size()<all.getSize()) {
                List<Company> all1 = companyRepository.findAll();
                Page<Company> empty = new PageImpl<>(all1);
                Page<CompanyDto> dtoPage = companyMapper.toDtoPage(empty);
                log.info("{} gave",empty);
                return dtoPage;
            }
            Page<CompanyDto> dtoPage = companyMapper.toDtoPage(all);
            log.info("{} gave",dtoPage);
            return dtoPage;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
