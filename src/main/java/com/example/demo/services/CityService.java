package com.example.demo.services;

import com.example.demo.dto.CityDto;
import com.example.demo.dtoRequest.CityDtoR;
import com.example.demo.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface CityService {
    CityDtoR cityCreate(CityDtoR cityDtoR);
    CityDtoR cityEdit(CityDtoR cityDtoR);
    CityDtoR cityRead(String name);
    void cityDelete(String name);
    Page<CityDtoR> getAllCities(int page,int size);

    CityDtoR cityRead(UUID id);
}
