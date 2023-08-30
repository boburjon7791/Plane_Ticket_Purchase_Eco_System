package com.example.demo.mappers;

import com.example.demo.dto.CompanyDto;
import com.example.demo.entities.Company;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
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
    Company toEntity(CompanyDto companyDto,CycleAvoidingMappingContext context);
    CompanyDto toDto(Company company, CycleAvoidingMappingContext context);

    @BeforeMapping
    default void handleAuthUserDto(Company company, @MappingTarget CompanyDto companyDto, @Context CycleAvoidingMappingContext context) {

        if (company == null) {
            return;
        }
        context.storeMappedInstance(company, companyDto);
    }

    // Context parametrini delegat qilamiz
    @BeforeMapping
    default void handleAuthUser(CompanyDto companyDto, @MappingTarget Company company, @Context CycleAvoidingMappingContext context) {
        if (companyDto == null) {
            return;
        }
        context.storeMappedInstance(companyDto, company);
    }

    default Page<CompanyDto> toDtoPage(Page<Company> companies){
        if (companies==null || companies.isEmpty()) {
            return null;
        }
        return companies.map(this::toDto);
    }
    // TODO: 29/08/2023 set lar
}
