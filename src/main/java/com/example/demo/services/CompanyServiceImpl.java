package com.example.demo.services;

import com.example.demo.dto.CompanyDto;
import com.example.demo.dtoRequest.CompanyDtoR;
import com.example.demo.entities.Company;
import com.example.demo.exceptions.ForbiddenAccessException;
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
    public CompanyDtoR companyCreate(CompanyDtoR companyDtoR) {
        try {
            if (companyRepository.existsByName(companyDtoR.name)) {
                throw new ForbiddenAccessException();
            }
            Company company = companyMapper.toEntity(companyDtoR);
            Company save = companyRepository.save(company);
            CompanyDtoR dto = companyMapper.toDto(save);
            log.info("{} created",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public CompanyDtoR companyEdit(CompanyDtoR companyDtoR) {
        try {
            Company company = companyMapper.toEntity(companyDtoR);
            Company company1 = companyRepository.findById(company.getId()).orElseThrow();
                if (!company1.getAgent().equals(company.getAgent())) {
                    throw new ForbiddenAccessException();
                }
                if (!company1.getAirports().equals(company.getAirports())) {
                    throw new ForbiddenAccessException();
                }
            Company save = companyRepository.save(company);
            CompanyDtoR dto = companyMapper.toDto(save);
            log.info("{} updated",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public CompanyDtoR companyGet(String name) {
        try {
            Company company = companyRepository.findByName(name);
            CompanyDtoR dto = companyMapper.toDto(company);
            log.info("{} gave",dto);
//            setNull(dto);
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
    public Page<CompanyDtoR> getAllCompanies(int page,int size) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Company> all = companyRepository.findAll(pageable);
            all.getContent().forEach(System.out::println);
            if (all.getContent().size()<all.getSize()) {
                List<Company> all1 = companyRepository.findAll();
                Page<Company> empty = new PageImpl<>(all1);
                Page<CompanyDtoR> dtoPage = companyMapper.toDtoPage(empty);
                log.info("{} gave",empty);
//                dtoPage.forEach(this::setNull);
                return dtoPage;
            }
            Page<CompanyDtoR> dtoPage = companyMapper.toDtoPage(all);
            log.info("{} gave",dtoPage);
//            dtoPage.forEach(this::setNull);
            return dtoPage;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public CompanyDtoR companyGet(UUID id) {
        try {
            Optional<Company> byId = companyRepository.findById(id);
            CompanyDtoR dto = companyMapper.toDto(byId.orElseThrow());
            log.info("{} gave",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    private void setNull(CompanyDto dto) {
        dto.getAgent().forEach(authUser -> {
            authUser.setCompany(null);
            authUser.setFlights(null);
            authUser.setActivateCodes(null);
        });
        dto.getAirports().forEach(airport -> {
            airport.setCompany(null);
            airport.setCities(null);
            airport.setFlights(null);
        });
    }
}
