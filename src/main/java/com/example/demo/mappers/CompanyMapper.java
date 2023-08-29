package com.example.demo.mappers;

import com.example.demo.dto.CompanyDto;
import com.example.demo.entities.Company;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyMapper {
    Company toEntity(CompanyDto companyDto);
    // TODO: 29/08/2023  set lar
    CompanyDto toDto(Company company);
    // TODO: 29/08/2023 set lar
}
