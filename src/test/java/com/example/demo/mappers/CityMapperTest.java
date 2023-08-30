package com.example.demo.mappers;

import com.example.demo.dto.CityDto;
import com.example.demo.entities.Airport;
import com.example.demo.entities.City;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CityMapperTest {

    @Test
    void toEntity() {
        Airport airport = Airport.builder()
                .id(UUID.randomUUID())
                .name("toshkent-airport")
                .build();
        CityDto cityDto = CityDto.builder()
                .id(UUID.randomUUID())
                .name("toshkent")
                .airports(Set.of(airport))
                .build();
        CityMapper cityMapper = Mappers.getMapper(CityMapper.class);
        System.out.println("cityDto = " + cityDto);
        City city1 = cityMapper.toEntity(cityDto);
        System.out.println("city1.getAirports() = " + city1.getAirports());
        System.out.println("city1 = " + city1);
    }

    @Test
    void toDto() {
        Airport airport = Airport.builder()
                .id(UUID.randomUUID())
                .name("toshkent-airport")
                .build();
        City city = City.builder()
                .id(UUID.randomUUID())
                .name("toshkent")
                .airports(Set.of(airport))
                .build();
        airport.setCities(Set.of(city));

        System.out.println("city = " + city);
        CityMapper cityMapper = Mappers.getMapper(CityMapper.class);
        CityDto dto = cityMapper.toDto(city);
        System.out.println("dto = " + dto);
    }
}