package com.example.demo.services;

import com.example.demo.dto.CityDto;
import com.example.demo.entities.City;
import com.example.demo.mappers.CityMapper;
import com.example.demo.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    public final CityRepository cityRepository;
    public final CityMapper cityMapper;
    @Override
    public CityDto cityCreate(CityDto cityDto) {
        City city = cityMapper.toEntity(cityDto);
        try {
            City save = cityRepository.save(city);
            CityDto dto = cityMapper.toDto(save);
            log.info("{} created",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public CityDto cityEdit(CityDto cityDto) {
        City city = cityMapper.toEntity(cityDto);
        try {
            City save = cityRepository.save(city);
            CityDto dto = cityMapper.toDto(save);
            log.info("{} updated",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public CityDto cityRead(String name) {
        try {
            City city = cityRepository.findByName(name);
            CityDto dto = cityMapper.toDto(city);
            log.info("{} gave",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public void cityDelete(String name) {
        try {
            cityRepository.deleteByName(name);
            log.info("{} city deleted",name);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public Page<CityDto> getAllCities(int size,int page) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<City> all = cityRepository.findAll(pageable);
            Page<CityDto> dtoPage = cityMapper.toDtoPage(all);
            log.info("{} gave",dtoPage);
            return dtoPage;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
