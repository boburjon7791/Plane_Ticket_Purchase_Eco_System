package com.example.demo.mappers;

import com.example.demo.dto.CityDto;
import com.example.demo.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CityMapper {
//    CityMapper CITY_MAPPER = Mappers.getMapper(CityMapper.class);
    City toEntity(CityDto cityDto);
    CityDto toDto(City city);

    default Page<CityDto> toDtoPage(Page<City> cities){
        if (cities==null || cities.isEmpty()) {
            return null;
        }
        return cities.map(this::toDto);
    }

}
