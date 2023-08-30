package com.example.demo.mappers;

import com.example.demo.dto.FlightDto;
import com.example.demo.entities.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface FlightMapper {
    FlightMapper FLIGHT_MAPPER = Mappers.getMapper(FlightMapper.class);
    Flight toEntity(FlightDto flightDto);
    FlightDto toDto(Flight flight);
    default Page<FlightDto> toDtoPage(Page<Flight> flights){
        if (flights==null || flights.isEmpty()) {
            return null;
        }
        return flights.map(FLIGHT_MAPPER::toDto);
    }
}
