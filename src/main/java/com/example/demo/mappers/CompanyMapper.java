package com.example.demo.mappers;

import com.example.demo.dtoRequest.CityDtoR;
import com.example.demo.dtoRequest.CompanyDtoR;
import com.example.demo.entities.Company;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CompanyMapper {
//    CompanyMapper COMPANY_MAPPER = Mappers.getMapper(CompanyMapper.class);
    default Company toEntity(CompanyDtoR companyDtoR){
        return Company.builder()
                .name(companyDtoR.name)
                .airports(companyDtoR.getAirports())
                .agent(companyDtoR.getAgents())
                .id(companyDtoR.getId())
                .build();
    }
    default CompanyDtoR toDto(Company company){
        return CompanyDtoR.builder()
                .name(company.getName())
                .id(company.getId())
                .build();
    }
    default Page<CompanyDtoR> toDtoPage(Page<Company> companies){
        if (companies==null || companies.isEmpty()) {
            return null;
        }
        return companies.map(this::toDto);
    }
}
