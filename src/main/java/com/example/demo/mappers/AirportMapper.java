package com.example.demo.mappers;

import com.example.demo.dto.AirportDto;
import com.example.demo.entities.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.LinkedList;
import java.util.List;
@Mapper
public interface AirportMapper {
    AirportMapper AIRPORT_MAPPER = Mappers.getMapper(AirportMapper.class);
    Airport toEntity(AirportDto airportDto);

    // TODO: 29/08/2023 set lar
    AirportDto toDto(Airport airport);
    // TODO: 29/08/2023 set lar

    default List<AirportDto> toDtoList(List<Airport> airports){
        if (airports==null || airports.isEmpty()) {
            return null;
        }
        List<AirportDto> airportDtoList = new LinkedList<>();
        airports.forEach(airport -> airportDtoList.add(AIRPORT_MAPPER.toDto(airport)));
        return airportDtoList;
    }
}
