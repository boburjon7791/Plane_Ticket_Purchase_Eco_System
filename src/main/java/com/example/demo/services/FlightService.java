package com.example.demo.services;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.dto.FlightDto;
import com.example.demo.entities.AuthUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface FlightService {
    FlightDto flightCreate(FlightDto flightDto);
    FlightDto flightEdit(FlightDto flightDto);
    Page<FlightDto> flightsGet(AuthUserDto authUserDto,int size, int page);
    void flightDelete(UUID id);
    Page<FlightDto> getAllFlights(int limit, int page);
}
