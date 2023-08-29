package com.example.demo.services;

import com.example.demo.dto.FlightDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface FlightService {
    FlightDto flightCreate(FlightDto flightDto);
    FlightDto flightEdit(FlightDto flightDto);
    List<FlightDto> flightsGet(Long authUserId);
    void flightDelete(UUID id);
    Page<FlightDto> getAllFlights(int limit, int page);
}
