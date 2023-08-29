package com.example.demo.services;

import com.example.demo.dto.AirportDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AirportService {
    AirportDto createAirport(AirportDto airportDto);
    AirportDto getAirport(String name);
    AirportDto updateAirport(AirportDto airportDto);
    void deleteAirport(String name);
    Page<AirportDto> getAll(int size,int page);
}
