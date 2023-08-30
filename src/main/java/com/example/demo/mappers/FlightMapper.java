package com.example.demo.mappers;

import com.example.demo.dto.FlightDto;
import com.example.demo.entities.Flight;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface FlightMapper {
//    FlightMapper FLIGHT_MAPPER = Mappers.getMapper(FlightMapper.class);
    Flight toEntity(FlightDto flightDto);
    FlightDto toDto(Flight flight);
    Flight toEntity(FlightDto flightDto, CycleAvoidingMappingContext context);
    FlightDto toDto(Flight flight, CycleAvoidingMappingContext context);
    @BeforeMapping
    default void handleAuthUserDto(Flight flight, @MappingTarget FlightDto flightDto, @Context CycleAvoidingMappingContext context) {

        if (flight == null) {
            return;
        }
        context.storeMappedInstance(flight, flightDto);
    }

    // Context parametrini delegat qilamiz
    @BeforeMapping
    default void handleAuthUser(FlightDto flightDto, @MappingTarget Flight flight, @Context CycleAvoidingMappingContext context) {
        if (flightDto == null) {
            return;
        }
        context.storeMappedInstance(flightDto, flight);
    }
    default Page<FlightDto> toDtoPage(Page<Flight> flights){
        if (flights==null || flights.isEmpty()) {
            return null;
        }
        return flights.map(this::toDto);
    }
}
