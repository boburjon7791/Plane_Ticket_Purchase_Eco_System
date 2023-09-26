package com.example.demo.mappers;

import com.example.demo.dto.CityDto;
import com.example.demo.dtoRequest.CityDtoR;
import com.example.demo.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CityMapper {
    CityMapper CITY_MAPPER = Mappers.getMapper(CityMapper.class);
    default City toEntity(CityDtoR cityDtoR){
        return City.builder()
                .name(cityDtoR.name)
                .id(cityDtoR.id)
                .gmt(cityDtoR.getGmt())
                .airports(cityDtoR.getAirports())
                .flights(cityDtoR.getFlights())
                .flights2(cityDtoR.getFlights2())
                .build();
    }
    default CityDtoR toDto(City city){
        return CityDtoR.builder()
                .id(city.getId())
                .gmt(city.getGmt())
                .name(city.getName())
                .build();
    }

    default Page<CityDtoR> toDtoPage(Page<City> cities){
        if (cities==null || cities.isEmpty()) {
            return null;
        }
        return cities.map(this::toDto);
    }

}
