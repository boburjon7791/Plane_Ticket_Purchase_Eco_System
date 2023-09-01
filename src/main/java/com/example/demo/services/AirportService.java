package com.example.demo.services;

import com.example.demo.dto.AirportDto;
import com.example.demo.dtoRequest.AirportDtoR;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AirportService {
    AirportDtoR createAirport(AirportDtoR airportDtoR, HttpServletResponse res);
    AirportDtoR getAirport(String name);
    AirportDtoR updateAirport(AirportDtoR airportDtoR);
    void deleteAirport(String name);
    Page<AirportDtoR> getAll(int size,int page);

    AirportDtoR getAirport(UUID id);
}
