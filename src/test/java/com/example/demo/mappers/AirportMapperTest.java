package com.example.demo.mappers;

import com.example.demo.dto.AirportDto;
import com.example.demo.dto.CompanyDto;
import com.example.demo.entities.Airport;
import com.example.demo.entities.Company;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AirportMapperTest {

    @Test
    void toEntity() {
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name("t-way")
                .build();
        AirportDto dto = AirportDto.builder()
                .id(UUID.randomUUID())
                .name("airport-one")
                .company(company)
                .build();
        System.out.println("dto = " + dto);
        AirportMapper airportMapper = Mappers.getMapper(AirportMapper.class);
        Airport airport = airportMapper.toEntity(dto,new CycleAvoidingMappingContext());
        System.out.println("airport = " + airport);
    }

    @Test
    void toDto() {
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name("t-way")
                .build();
        Airport airport = Airport.builder()
                .id(UUID.randomUUID())
                .name("airport-one")
                .company(company)
                .build();
        HashSet<Airport> objects = new HashSet<>();
        objects.add(airport);
        company.setAirports(objects);
        System.out.println("airport = " + airport);
        AirportMapper airportMapper = Mappers.getMapper(AirportMapper.class);
        AirportDto dto = airportMapper.toDto(airport,new CycleAvoidingMappingContext());
        System.out.println("dto = " + dto);
    }
}