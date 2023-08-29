package com.example.demo.mappers;

import com.example.demo.dto.CityDto;
import com.example.demo.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
@Mapper
public interface CityMapper {
    CityMapper CITY_MAPPER = Mappers.getMapper(CityMapper.class);
    City toEntity(CityDto cityDto);

    // TODO: 29/08/2023 set lar
    // TODO: 29/08/2023 set lar
    CityDto toDto(City city);

    default Page<CityDto> toDtoPage(Page<City> cities){
        if (cities==null || cities.isEmpty()) {
            return null;
        }
        return cities.map(CITY_MAPPER::toDto);
    }

}
