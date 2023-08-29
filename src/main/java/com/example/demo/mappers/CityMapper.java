package com.example.demo.mappers;

import com.example.demo.dto.CityDto;
import com.example.demo.entities.City;
import org.mapstruct.Mapper;

@Mapper
public interface CityMapper {
    City toEntity(CityDto cityDto);

    // TODO: 29/08/2023 set lar
    CityDto toDto(City city);
    // TODO: 29/08/2023 set lar
}
