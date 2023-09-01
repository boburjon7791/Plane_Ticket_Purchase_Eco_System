package com.example.demo.mappers;

import com.example.demo.dtoRequest.FlightDtoR;
import com.example.demo.entities.Airport;
import com.example.demo.entities.City;
import com.example.demo.entities.Flight;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface FlightMapper {
//    FlightMapper FLIGHT_MAPPER = Mappers.getMapper(FlightMapper.class);
    default Flight toEntity(FlightDtoR flightDtoR, City to, City from, Airport airport){
        return Flight.builder()
                .id(flightDtoR.getId())
                .to(to)
                .from(from)
                .airport(airport)
                .authUsers(flightDtoR.getAuthUsers())
                .fromTime(flightDtoR.getFromTime())
                .toTime(flightDtoR.getToTime())
                .build();
    }
    default FlightDtoR toDto(Flight flight){
        return FlightDtoR.builder()
                .fromTime(flight.getFromTime())
                .toTime(flight.getToTime())
                .fromId(flight.getFrom().getId())
                .toId(flight.getTo().getId())
                .airportId(flight.getAirport().getId())
                .id(flight.getId())
                .build();
    }
    default Page<FlightDtoR> toDtoPage(Page<Flight> flights){
        if (flights==null || flights.isEmpty()) {
            return null;
        }
        return flights.map(this::toDto);
    }
}
