package com.example.demo.services;

import com.example.demo.dto.AirportDto;
import com.example.demo.entities.Airport;
import com.example.demo.entities.Company;
import com.example.demo.mappers.AirportMapper;
import com.example.demo.repositories.AirportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    public final AirportRepository airportRepository;
    public final AirportMapper airportMapper;
    @Override
    public AirportDto createAirport(AirportDto airportDto) {
        try {
            Airport airport = airportMapper.toEntity(airportDto);
            Airport save = airportRepository.save(airport);
            AirportDto dto = airportMapper.toDto(save);
            log.info("{} saved",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public AirportDto getAirport(String name) {
        try {
            Airport airport = airportRepository.findByName(name);
            AirportDto dto = airportMapper.toDto(airport);
            log.info("{} gave",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public AirportDto updateAirport(AirportDto airportDto) {
        try {
            Airport airport = airportMapper.toEntity(airportDto);
            Airport save = airportRepository.save(airport);
            AirportDto dto = airportMapper.toDto(save);
            log.info("{} updated",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public void deleteAirport(String name) {
        try {
            airportRepository.deleteByName(name);
            log.info("{} airport deleted",name);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public Page<AirportDto> getAll(int size, int page) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Airport> all = airportRepository.findAll(pageable);
            return airportMapper.toDtoPage(all);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 30/08/2023 log
            return null;
        }
    }
}
