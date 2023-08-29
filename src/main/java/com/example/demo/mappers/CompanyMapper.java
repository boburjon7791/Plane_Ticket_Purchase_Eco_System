package com.example.demo.mappers;

import com.example.demo.dto.CompanyDto;
import com.example.demo.entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mapper
public interface CompanyMapper {
    CompanyMapper COMPANY_MAPPER = Mappers.getMapper(CompanyMapper.class);
    Company toEntity(CompanyDto companyDto);
    // TODO: 29/08/2023  set lar
    CompanyDto toDto(Company company);

    default List<CompanyDto> toDtoList(List<Company> companies){
        if (companies==null || companies.isEmpty()) {
            return null;
        }
        List<CompanyDto> companyDtoList = new LinkedList<>();
        companies.forEach(company -> companyDtoList.add(COMPANY_MAPPER.toDto(company)));
        return companyDtoList;
    }
    // TODO: 29/08/2023 set lar
}
