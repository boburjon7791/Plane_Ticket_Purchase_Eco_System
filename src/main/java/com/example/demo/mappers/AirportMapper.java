package com.example.demo.mappers;

import com.example.demo.dtoRequest.AirportDtoR;
import com.example.demo.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AirportMapper {
    AirportMapper AIRPORT_MAPPER = Mappers.getMapper(AirportMapper.class);
    default Airport toEntity(AirportDtoR airportDtoR,Company company){
        return Airport.builder()
                .name(airportDtoR.getName())
                .company(company)
                .id(airportDtoR.getId())
                .flights(airportDtoR.getFlights())
                .cities(airportDtoR.getCities())
                .build();
    }
    default AirportDtoR toDto(Airport airport, Company company){
        return AirportDtoR.builder()
                .name(airport.getName())
                .companyId(company.getId())
                .id(airport.getId())
                .build();
    }

    default Page<AirportDtoR> toDtoPage(Page<Airport> airports){
        if (airports==null || airports.isEmpty()) {
            return null;
        }
        return airports.map(airport -> toDto(airport,airport.getCompany()));
    }
}
