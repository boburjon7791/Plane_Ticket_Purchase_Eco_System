package com.example.demo.services;

import com.example.demo.dtoRequest.CompanyDtoR;
import com.example.demo.entities.Company;
import com.example.demo.exceptions.ForbiddenAccessException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.CompanyMapper;
import com.example.demo.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    public final CompanyRepository companyRepository;
    public final CompanyMapper companyMapper;
    @Override
    public CompanyDtoR companyCreate(CompanyDtoR companyDtoR) {
            if (companyRepository.existsByName(companyDtoR.name)) {
                throw new ForbiddenAccessException();
            }
            Company company = companyMapper.toEntity(companyDtoR);
            Company save = companyRepository.save(company);
            CompanyDtoR dto = companyMapper.toDto(save);
            log.info("{} created",dto);
            return dto;
    }

    @Override
    public CompanyDtoR companyEdit(CompanyDtoR companyDtoR) {
            Company company = companyMapper.toEntity(companyDtoR);
            Company company1 = companyRepository.findById(company.getId()).orElseThrow(NotFoundException::new);
                if (!company1.getAgent().equals(company.getAgent())) {
                    throw new ForbiddenAccessException();
                }
                if (!company1.getAirports().equals(company.getAirports())) {
                    throw new ForbiddenAccessException();
                }
            Company save = companyRepository.save(company);
            CompanyDtoR dto = companyMapper.toDto(save);
            log.info("{} updated",dto);
            return dto;
    }

    @Override
    public CompanyDtoR companyGet(String name) {
            Company company = companyRepository.findByName(name);
            CompanyDtoR dto = companyMapper.toDto(company);
            log.info("{} gave",dto);
            return dto;
    }

    @Override
    public Page<CompanyDtoR> getAllCompanies(int page,int size) {
            Pageable pageable = PageRequest.of(page,size);
            Page<Company> all = companyRepository.findAll(pageable);
            all.getContent().forEach(System.out::println);
            if (all.getContent().size()<all.getSize()) {
                List<Company> all1 = companyRepository.findAll();
                Page<Company> empty = new PageImpl<>(all1);
                Page<CompanyDtoR> dtoPage = companyMapper.toDtoPage(empty);
                log.info("{} gave",empty);
                return dtoPage;
            }
            Page<CompanyDtoR> dtoPage = companyMapper.toDtoPage(all);
            log.info("{} gave",dtoPage);
            return dtoPage;
    }

    @Override
    public CompanyDtoR companyGet(UUID id) {
            Optional<Company> byId = companyRepository.findById(id);
            CompanyDtoR dto = companyMapper.toDto(byId
                    .orElseThrow(NotFoundException::new));
            log.info("{} gave",dto);
            return dto;
    }
}
