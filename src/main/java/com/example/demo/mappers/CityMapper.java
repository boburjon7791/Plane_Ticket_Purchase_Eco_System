package com.example.demo.mappers;

import com.example.demo.dto.CityDto;
import com.example.demo.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.LinkedList;
import java.util.List;

@Mapper
public interface CityMapper {
    CityMapper CITY_MAPPER = Mappers.getMapper(CityMapper.class);
    City toEntity(CityDto cityDto);

    // TODO: 29/08/2023 set lar
    // TODO: 29/08/2023 set lar
    CityDto toDto(City city);

    default List<CityDto> toDtoList(List<City> cities){
        if (cities==null || cities.isEmpty()) {
            return null;
        }
        List<CityDto> cityDtoList = new LinkedList<>();
        cities.forEach(city -> cityDtoList.add(CITY_MAPPER.toDto(city)));
        return cityDtoList;
    }

}
