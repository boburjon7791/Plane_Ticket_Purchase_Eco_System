package com.example.demo.services;

import com.example.demo.dto.AirportDto;
import com.example.demo.dto.CityDto;
import com.example.demo.dtoRequest.CityDtoR;
import com.example.demo.entities.Airport;
import com.example.demo.entities.City;
import com.example.demo.exceptions.ForbiddenAccessException;
import com.example.demo.mappers.CityMapper;
import com.example.demo.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    public final CityRepository cityRepository;
    public final CityMapper cityMapper;
    private static String setGmt(String city){
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        Optional<String> first = zoneIds.stream().filter(s -> s.toUpperCase().contains(city))
                .findFirst();
        return ZoneId.of(first.orElseThrow()).getRules().toString();
    }
    @Override
    public CityDtoR cityCreate(CityDtoR cityDtoR) {
        if (cityRepository.existsByName(cityDtoR.name)) {
            throw new ForbiddenAccessException();
        }
        City city = cityMapper.toEntity(cityDtoR);
        if (city.getGmt().equals("")) {
            String s = setGmt(setGmt(city.getName()));
            city.setGmt(s);
        }
        try {
            City save = cityRepository.save(city);
            CityDtoR dto = cityMapper.toDto(save);
            log.info("{} created",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    private void setNull(CityDto dto) {
        dto.getAirports().forEach(airport -> {
            airport.setCities(null);
            airport.setCompany(null);
            airport.setFlights(null);
        });
    }

    @Override
    public CityDtoR cityEdit(CityDtoR cityDtoR) {
        City city = cityMapper.toEntity(cityDtoR);
        if (city.getGmt().equals("")) {
            String s = setGmt(setGmt(city.getName()));
            city.setGmt(s);
        }
        try {
            City city1 = cityRepository.findById(city.getId()).orElseThrow();
                if (!city1.getAirports().equals(city.getAirports())) {
                    throw new ForbiddenAccessException();
                }
                if (!city1.getFlights().equals(city.getFlights())) {
                    throw new ForbiddenAccessException();
                }
            City save = cityRepository.save(city);
            CityDtoR dto = cityMapper.toDto(save);
            log.info("{} updated",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public CityDtoR cityRead(String name) {
        try {
            City city = cityRepository.findByName(name);
            CityDtoR dto = cityMapper.toDto(city);
            log.info("{} gave",dto);
//            setNull(dto);
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
    public Page<CityDtoR> getAllCities(int size,int page) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<City> all = cityRepository.findAll(pageable);
            if (all.getContent().size()<all.getSize()) {
                List<City> all1 = cityRepository.findAll();
                Page<City> empty = new PageImpl<>(all1);
                Page<CityDtoR> dtoPage = cityMapper.toDtoPage(empty);
                log.info("{} gave",empty);
//                dtoPage.forEach(this::setNull);
                return dtoPage;
            }
            Page<CityDtoR> dtoPage = cityMapper.toDtoPage(all);
//            dtoPage.forEach(this::setNull);
            log.info("{} gave",dtoPage);
            return dtoPage;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public CityDtoR cityRead(UUID id) {
        try {
            Optional<City> byId = cityRepository.findById(id);
            CityDtoR dto = cityMapper.toDto(byId.orElseThrow());
            log.info("{} gave",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
