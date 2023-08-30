package com.example.demo.mappers;

import com.example.demo.dto.AirportDto;
import com.example.demo.entities.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.LinkedList;
import java.util.List;
@Mapper
public interface AirportMapper {
    AirportMapper AIRPORT_MAPPER = Mappers.getMapper(AirportMapper.class);
    Airport toEntity(AirportDto airportDto);
    AirportDto toDto(Airport airport);

    default Page<AirportDto> toDtoPage(Page<Airport> airports){
        if (airports==null || airports.isEmpty()) {
            return null;
        }
        return airports.map(AIRPORT_MAPPER::toDto);
    }
}
