package com.example.demo.mappers;

import com.example.demo.dto.AirportDto;
import com.example.demo.dto.AuthUserDto;
import com.example.demo.entities.Airport;
import com.example.demo.entities.AuthUser;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AirportMapper {
//    AirportMapper AIRPORT_MAPPER = Mappers.getMapper(AirportMapper.class);
    Airport toEntity(AirportDto airportDto);
    AirportDto toDto(Airport airport);
    Airport toEntity(AirportDto airportDto, CycleAvoidingMappingContext context);
    AirportDto toDto(Airport airport, CycleAvoidingMappingContext context);

    @BeforeMapping
    default void handleAirportDto(Airport airport, @MappingTarget AirportDto airportDto, @Context CycleAvoidingMappingContext context) {

        if (airport == null) {
            return;
        }
        context.storeMappedInstance(airport, airportDto);
    }

    // Context parametrini delegat qilamiz
    @BeforeMapping
    default void handleAirport(AirportDto airportDto, @MappingTarget Airport airport, @Context CycleAvoidingMappingContext context) {
        if (airportDto == null) {
            return;
        }
        context.storeMappedInstance(airportDto, airport);
    }

    default Page<AirportDto> toDtoPage(Page<Airport> airports){
        if (airports==null || airports.isEmpty()) {
            return null;
        }
        return airports.map(this::toDto);
    }
}
