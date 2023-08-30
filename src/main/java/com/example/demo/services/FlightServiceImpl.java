package com.example.demo.services;


import com.example.demo.dto.FlightDto;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Flight;
import com.example.demo.mappers.FlightMapper;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.repositories.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    public final FlightRepository flightRepository;
    public final FlightMapper flightMapper;
    private final AgentService agentService;
    private final AuthUserRepository authUserRepository;

    @Override
    public FlightDto flightCreate(FlightDto flightDto) {
        try {
            Flight flight = flightMapper.toEntity(flightDto);
            Flight save = flightRepository.save(flight);
            FlightDto dto = flightMapper.toDto(save);
            log.info("{} created",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public FlightDto flightEdit(FlightDto flightDto) {
        try {
            UUID id = flightDto.getId();
            Optional<Flight> byId = flightRepository.findById(id);
            LocalDateTime oldTime = byId.orElseThrow().getLocalDateTime();
            Flight flight = flightMapper.toEntity(flightDto);
            Flight save = flightRepository.save(flight);
            agentService.sendReportEditFlight(save,oldTime);
            return flightMapper.toDto(save);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 29/08/2023  log
            return null;
        }
    }

    @Override
    public FlightDto flightsGet(UUID id) {
        try {
            Optional<Flight> byId = flightRepository.findById(id);
            Flight flight = byId.orElseThrow(RuntimeException::new);
            return flightMapper.toDto(flight);
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

    @Override
    public void flightReserve(FlightDto flightDto, UUID userID) {
        try {
            Optional<AuthUser> byId = authUserRepository.findById(userID);
            AuthUser authUser = byId.orElseThrow();
            flightDto.setId(userID);
            flightDto.getAuthUsers().add(authUser);
            flightDto.setAuthUsers(flightDto.getAuthUsers());
            Flight flight = flightMapper.toEntity(flightDto);
            flightRepository.save(flight);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void flightCancel(FlightDto flightDto, UUID userID) {
        try {
        Optional<AuthUser> byId = authUserRepository.findById(userID);
        AuthUser authUser = byId.orElseThrow();
        flightDto.setId(userID);
        flightDto.getAuthUsers().remove(authUser);
        flightDto.setAuthUsers(flightDto.getAuthUsers());
        Flight flight = flightMapper.toEntity(flightDto);
        flightRepository.save(flight);
        log.info("{} user canceled {}",authUser,flight);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
        }
    }
}
