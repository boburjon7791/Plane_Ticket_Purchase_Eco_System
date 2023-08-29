package com.example.demo.services;

import com.example.demo.dto.CityDto;
import com.example.demo.entities.City;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CityService {
    CityDto cityCreate(CityDto cityDto);
    CityDto cityEdit(CityDto cityDto);
    CityDto cityRead(String name);
    void cityDelete(String name);
    List<CityDto> getAllCity();
}
