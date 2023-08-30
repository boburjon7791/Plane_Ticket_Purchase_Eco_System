package com.example.demo.services;

import com.example.demo.dto.AirportDto;
import com.example.demo.entities.Airport;
import com.example.demo.mappers.AirportMapper;
import com.example.demo.repositories.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    public final AirportRepository airportRepository;
    public final AirportMapper airportMapper;
    @Override
    public AirportDto createAirport(AirportDto airportDto) {
        try {
            Airport airport = airportMapper.toEntity(airportDto);
            Airport save = airportRepository.save(airport);
            return airportMapper.toDto(save);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 30/08/2023  log
            return null;
        }
    }

    @Override
    public AirportDto getAirport(String name) {
        try {
            Airport airport = airportRepository.findByName(name);
            return airportMapper.toDto(airport);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 30/08/2023  log
            return null;
        }
    }

    @Override
    public AirportDto updateAirport(AirportDto airportDto) {
        try {
            Airport airport = airportMapper.toEntity(airportDto);
            Airport save = airportRepository.save(airport);
            return airportMapper.toDto(save);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 30/08/2023  log
            return null;
        }
    }

    @Override
    public void deleteAirport(String name) {
        try {
            airportRepository.deleteByName(name);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 30/08/2023 log
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
