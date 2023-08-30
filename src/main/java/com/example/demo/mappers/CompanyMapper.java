package com.example.demo.mappers;

import com.example.demo.dto.CompanyDto;
import com.example.demo.entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface CompanyMapper {
//    CompanyMapper COMPANY_MAPPER = Mappers.getMapper(CompanyMapper.class);
    Company toEntity(CompanyDto companyDto);
    CompanyDto toDto(Company company);

    default Page<CompanyDto> toDtoPage(Page<Company> companies){
        if (companies==null || companies.isEmpty()) {
            return null;
        }
        return companies.map(this::toDto);
    }
    // TODO: 29/08/2023 set lar
}
