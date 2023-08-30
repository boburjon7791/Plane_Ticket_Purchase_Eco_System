package com.example.demo.mappers;

import com.example.demo.dto.CityDto;
import com.example.demo.entities.City;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CityMapper {
//    CityMapper CITY_MAPPER = Mappers.getMapper(CityMapper.class);
    City toEntity(CityDto cityDto);
    CityDto toDto(City city);
    City toEntity(CityDto cityDto, CycleAvoidingMappingContext context);
    CityDto toDto(City city, CycleAvoidingMappingContext context);

    @BeforeMapping
    default void handleAuthUserDto(City city, @MappingTarget CityDto cityDto, @Context CycleAvoidingMappingContext context) {

        if (city == null) {
            return;
        }
        context.storeMappedInstance(city, cityDto);
    }

    // Context parametrini delegat qilamiz
    @BeforeMapping
    default void handleAuthUser(CityDto cityDto, @MappingTarget City city, @Context CycleAvoidingMappingContext context) {
        if (cityDto == null) {
            return;
        }
        context.storeMappedInstance(cityDto, city);
    }

    default Page<CityDto> toDtoPage(Page<City> cities){
        if (cities==null || cities.isEmpty()) {
            return null;
        }
        return cities.map(this::toDto);
    }

}
