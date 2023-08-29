package com.example.demo.services;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.dto.FlightDto;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Flight;
import com.example.demo.mappers.AuthUserMapper;
import com.example.demo.mappers.FlightMapper;
import com.example.demo.repositories.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    public final FlightRepository flightRepository;
    public final FlightMapper flightMapper = FlightMapper.FLIGHT_MAPPER;
    @Override
    public FlightDto flightCreate(FlightDto flightDto) {
        try {
            Flight flight = flightMapper.toEntity(flightDto);
            Flight save = flightRepository.save(flight);
            return flightMapper.toDto(save);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023  log
            return null;
        }
    }

    @Override
    public FlightDto flightEdit(FlightDto flightDto) {
        try {
            Flight flight = flightMapper.toEntity(flightDto);
            Flight save = flightRepository.save(flight);
            return flightMapper.toDto(save);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023  log
            return null;
        }
    }

    @Override
    public Page<FlightDto> flightsGet(AuthUserDto authUserDto, int size, int page) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            AuthUser authUser = AuthUserMapper.AUTH_USER_MAPPER.toEntity(authUserDto);
            Page<Flight> flights = flightRepository.findAllByAuthUserId(authUser,pageable);
            return flightMapper.toDtoPage(flights);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
            return null;
        }
    }

    @Override
    public void flightDelete(UUID id) {
        try {
            flightRepository.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
        }
    }

    @Override
    public Page<FlightDto> getAllFlights(int limit, int page) {
        try {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Flight> flights = flightRepository.findAll(pageable);
            return flightMapper.toDtoPage(flights);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023 log
            return null;
        }
    }
}
