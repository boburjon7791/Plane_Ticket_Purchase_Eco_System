package com.example.demo.mappers;

import com.example.demo.dto.FlightDto;
import com.example.demo.entities.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

@Mapper
public interface FlightMapper {
    FlightMapper FLIGHT_MAPPER = Mappers.getMapper(FlightMapper.class);
    Flight toEntity(FlightDto flightDto);
    FlightDto toDto(Flight flight);
    default Page<FlightDto> toDtoList(Page<Flight> flights){
        if (flights==null || flights.isEmpty()) {
            return null;
        }
        List<FlightDto> flightDtoList = new LinkedList<>();
        Pageable pageable = flights.getPageable();
        flights.forEach(flight -> flightDtoList.add(FLIGHT_MAPPER.toDto(flight)));
        return new PageImpl<>(flightDtoList,pageable,flights.getTotalElements());
    }
    default List<FlightDto> toDtoList2(List<Flight> flights){
        if (flights==null || flights.isEmpty()) {
            return null;
        }
        List<FlightDto> flightDtoList = new LinkedList<>();
        flights.forEach(flight -> flightDtoList.add(FLIGHT_MAPPER.toDto(flight)));
        return flightDtoList;
    }
}
