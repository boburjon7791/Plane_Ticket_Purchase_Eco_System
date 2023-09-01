package com.example.demo.services;

import com.example.demo.dto.FlightDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface FlightService {
    FlightDto flightCreate(FlightDto flightDto, Map<String, String> param);
    FlightDto flightEdit(FlightDto flightDto, Map<String, String> param);
    FlightDto flightsGet(UUID id);
    void flightDelete(UUID id);
    Page<FlightDto> getAllFlights(int limit, int page);

    void flightReserve(UUID id, UUID userID);
    void flightCancel(UUID id, UUID userID);
}
