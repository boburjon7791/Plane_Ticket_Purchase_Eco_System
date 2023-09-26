package com.example.demo.services;

import com.example.demo.dtoRequest.FlightDtoR;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface FlightService {
    FlightDtoR flightCreate(FlightDtoR flightDtoR, Map<String, String> param);
    FlightDtoR flightEdit(FlightDtoR flightDtoR, Map<String, String> param);
    FlightDtoR flightsGet(UUID id);
    List<FlightDtoR> flightsGet(LocalDate time);
    Page<FlightDtoR> getAllFlights(int limit, int page);

    void flightReserve(UUID id, UUID userID);
    void flightCancel(UUID id, UUID userID);
}
