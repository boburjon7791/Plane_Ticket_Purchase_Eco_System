package com.example.demo.services;

import com.example.demo.dto.CityDto;
import com.example.demo.entities.City;
import com.example.demo.mappers.CityMapper;
import com.example.demo.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    public final CityRepository cityRepository;
    public final CityMapper cityMapper;
    @Override
    public CityDto cityCreate(CityDto cityDto) {
        City city = cityMapper.toEntity(cityDto);
        try {
            City save = cityRepository.save(city);
            return cityMapper.toDto(save);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
            return null;
        }
    }

    @Override
    public CityDto cityEdit(CityDto cityDto) {
        City city = cityMapper.toEntity(cityDto);
        try {
            City save = cityRepository.save(city);
            return cityMapper.toDto(save);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
            return null;
        }
    }

    @Override
    public CityDto cityRead(String name) {
        try {
            City city = cityRepository.findByName(name);
            return cityMapper.toDto(city);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
            return null;
        }
    }

    @Override
    public void cityDelete(String name) {
        try {
            cityRepository.deleteByName(name);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
        }
    }

    @Override
    public Page<CityDto> getAllCities(int size,int page) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<City> all = cityRepository.findAll(pageable);
            return cityMapper.toDtoPage(all);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
            return null;
        }
    }
}
