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
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Map;
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
    public FlightDto flightCreate(FlightDto flightDto, Map<String, String> param) {
        try {
            int hour=-7;
            int minute = -9;
            try {
                hour = Integer.parseInt(param.get("hour"));
                minute = Integer.parseInt(param.get("minute"));
            }catch (Exception e){
                e.printStackTrace();
                log.info("{}", Arrays.toString(e.getStackTrace()));
            }
            Flight flight = flightMapper.toEntity(flightDto);
            LocalDateTime toDateTime = flight.getFromTime().plusHours(hour).plusMinutes(minute).
                    atZone(ZoneId.of(flightDto.getTo().getName())).toLocalDateTime();
            flight.setToTime(toDateTime);
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
    public FlightDto flightEdit(FlightDto flightDto, Map<String, String> param) {
        try {
            int hour=-7;
            int minute=-10;
            try {
                hour= Integer.parseInt(param.get("hour"));
                minute= Integer.parseInt(param.get("minute"));
            }catch (Exception e){
                e.printStackTrace();
                log.info("{}",Arrays.toString(e.getStackTrace()));
            }
            UUID id = flightDto.getId();
            Optional<Flight> byId = flightRepository.findById(id);
            LocalDateTime oldFromTime = byId.orElseThrow().getFromTime();
            LocalDateTime oldToTime = byId.orElseThrow().getToTime();
            Flight flight = flightMapper.toEntity(flightDto);
            LocalDateTime toDateTime = flight.getFromTime().plusHours(hour).plusMinutes(minute).
                    atZone(ZoneId.of(flight.getFrom().getName())).toLocalDateTime();
            flight.setToTime(toDateTime);
            Flight save = flightRepository.save(flight);
            agentService.sendReportEditFlight(save,oldFromTime,oldToTime);
            FlightDto dto = flightMapper.toDto(save);
            log.info("{} updated",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public FlightDto flightsGet(UUID id) {
        try {
            Optional<Flight> byId = flightRepository.findById(id);
            Flight flight = byId.orElseThrow(RuntimeException::new);
            flight.setFromTime(flight.getFromTime().atZone(ZoneId.of(flight.getFrom().getName())).toLocalDateTime());
            flight.setToTime(flight.getToTime().atZone(ZoneId.of(flight.getTo().getName())).toLocalDateTime());
            FlightDto dto = flightMapper.toDto(flight);
            log.info("{} gave",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public void flightDelete(UUID id) {
        try {
            flightRepository.deleteById(id);
            log.info("{} flight deleted",id);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public Page<FlightDto> getAllFlights(int limit, int page) {
        try {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Flight> flights = flightRepository.findAll(pageable);
            flights.forEach(flight -> {
                flight.setFromTime(flight.getFromTime().atZone(ZoneId.of(flight.getFrom().getName())).toLocalDateTime());
                flight.setToTime(flight.getToTime().atZone(ZoneId.of(flight.getTo().getName())).toLocalDateTime());
            });
            Page<FlightDto> dtoPage = flightMapper.toDtoPage(flights);
            log.info("{} gave",dtoPage);
            return dtoPage;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public void flightReserve(UUID id, UUID userID) {
        try {
            Optional<AuthUser> byId = authUserRepository.findById(userID);
            AuthUser authUser = byId.orElseThrow();
            Optional<Flight> byId1 = flightRepository.findById(id);
            Flight flight = byId1.orElseThrow();
            flight.getAuthUsers().add(authUser);
            flightRepository.save(flight);
            log.info("{} user reserved {}",authUser,flight);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void flightCancel(UUID id, UUID userID) {
        try {
        Optional<AuthUser> byId = authUserRepository.findById(userID);
        AuthUser authUser = byId.orElseThrow();
        Optional<Flight> byId1 = flightRepository.findById(id);
        Flight flight = byId1.orElseThrow();
        flight.getAuthUsers().remove(authUser);
        flightRepository.save(flight);
        log.info("{} user canceled {}",authUser,flight);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
        }
    }
}
