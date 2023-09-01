package com.example.demo.services;

import com.example.demo.dto.FlightDto;
import com.example.demo.dtoRequest.FlightDtoR;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface FlightService {
    FlightDtoR flightCreate(FlightDtoR flightDtoR, Map<String, String> param);
    FlightDtoR flightEdit(FlightDtoR flightDtoR, Map<String, String> param);
    FlightDtoR flightsGet(UUID id);
    void flightDelete(UUID id);
    Page<FlightDtoR> getAllFlights(int limit, int page);

    void flightReserve(UUID id, UUID userID);
    void flightCancel(UUID id, UUID userID);
}
