package com.example.demo.services;

import com.example.demo.dtoRequest.CityDtoR;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CityService {
    CityDtoR cityCreate(CityDtoR cityDtoR);
    CityDtoR cityEdit(CityDtoR cityDtoR);
    CityDtoR cityRead(String name);
    Page<CityDtoR> getAllCities(int page,int size);

    CityDtoR cityRead(UUID id);
}
