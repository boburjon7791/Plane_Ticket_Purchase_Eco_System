package com.example.demo.mappers;

import com.example.demo.dto.AirportDto;
import com.example.demo.entities.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AirportMapper {
    AirportMapper AIRPORT_MAPPER = Mappers.getMapper(AirportMapper.class);
    Airport toEntity(AirportDto airportDto);

    // TODO: 29/08/2023 set lar
    AirportDto toDto(Airport airport);
    // TODO: 29/08/2023 set lar
}
