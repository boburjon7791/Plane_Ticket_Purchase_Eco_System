package com.example.demo.services;


import com.example.demo.dto.AirportDto;
import com.example.demo.dto.FlightDto;
import com.example.demo.dtoRequest.FlightDtoR;
import com.example.demo.entities.Airport;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.City;
import com.example.demo.entities.Flight;
import com.example.demo.mappers.FlightMapper;
import com.example.demo.repositories.AirportRepository;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRules;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    public final FlightRepository flightRepository;
    public final FlightMapper flightMapper;
    private final AgentService agentService;
    private final AuthUserRepository authUserRepository;

    @Override
    public FlightDtoR flightCreate(FlightDtoR flightDtoR, Map<String, String> param) {
        try {
            System.out.println(flightDtoR);
            int hour=0;
            int minute=0;
            try {
                hour = Integer.parseInt(param.get("hour"));
                System.out.println("---------------------");
                System.out.println("hour = " + hour);
                System.out.println("---------------------");
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            try {
                minute = Integer.parseInt(param.get("minute"));
            }catch (Exception e){
//                e.printStackTrace();
//                log.info("{}", Arrays.toString(e.getStackTrace()));
            }

            UUID airportId = flightDtoR.getAirportId();
            UUID fromId = flightDtoR.getFromId();
            UUID toId = flightDtoR.getToId();
            Optional<City> byIdFrom = cityRepository.findById(fromId);
            Optional<City> byIdTo = cityRepository.findById(toId);
            Optional<Airport> byIdAirport = airportRepository.findById(airportId);
            City fromCity = byIdFrom.orElseThrow();
            Airport airport = byIdAirport.orElseThrow();
            City toCity = byIdTo.orElseThrow();
            Flight flight = flightMapper.toEntity(flightDtoR, toCity, fromCity, airport);
            /*String from = fromCity.getName();

            /*Optional<String> first = ZoneId.getAvailableZoneIds().stream().
                    filter(s -> s.toUpperCase().contains(from.toUpperCase()))
                    .findFirst();
            String fromZone = first.orElseThrow();*/

            String gmtFrom = flight.getFrom().getGmt();
            Optional<String> fromZ = ZoneId.getAvailableZoneIds().stream()
                    .filter(s ->
                            ZoneId.of(s).getRules().toString().equals(gmtFrom))
                    .findFirst();
            String fromZone = fromZ.orElseThrow();

            String gmtTo = flight.getTo().getGmt();
            Optional<String> toZ = ZoneId.getAvailableZoneIds().stream()
                    .filter(s ->
                            ZoneId.of(s).getRules().toString().equals(gmtTo))
                    .findFirst();
            String toZone = toZ.orElseThrow();

            /*String to = toCity.getName();

            /*Optional<String> first2 = ZoneId.getAvailableZoneIds().stream().
                    filter(s -> s.toUpperCase().contains(to.toUpperCase()))
                    .findFirst();
            String toZone = first2.orElseThrow();*/

            ZonedDateTime zdtFrom = flight.getFromTime().atZone(ZoneId.of(fromZone));
            ZonedDateTime zdtTo = zdtFrom.plusHours(hour).plusMinutes(minute);
            ZonedDateTime zdtToTime = zdtTo.withZoneSameInstant(ZoneId.of(toZone));
            flight.setToTime(zdtToTime.toLocalDateTime());


            System.out.println("flight.getFromTime() = " + flight.getFromTime());
            System.out.println("flight.getToTime() = " + flight.getToTime());

            Flight save = flightRepository.save(flight);

            FlightDtoR dto = flightMapper.toDto(save);
            System.out.println(save);
            log.info("{} created",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public FlightDtoR flightEdit(FlightDtoR flightDtoR, Map<String, String> param) {
        try {
            int hour=-7;
            int minute=0;
            UUID fromId = flightDtoR.getFromId();
            UUID toId = flightDtoR.getToId();
            Optional<City> byIdFrom = cityRepository.findById(fromId);
            Optional<City> byIdTo = cityRepository.findById(toId);
            Optional<Airport> byIdAirport = airportRepository.findById(flightDtoR.getAirportId());
            try {
                try {
                    hour= Integer.parseInt(param.get("hour"));
                }catch (Exception e){
                    e.printStackTrace();
                    log.info("{}",Arrays.toString(e.getStackTrace()));
                    return null;
                }
                minute= Integer.parseInt(param.get("minute"));
            }catch (Exception e){
//                e.printStackTrace();
//                log.info("{}",Arrays.toString(e.getStackTrace()));
            }
            UUID id = flightDtoR.getId();
            Optional<Flight> byId = flightRepository.findById(id);
            LocalDateTime oldFromTime = byId.orElseThrow().getFromTime();
            LocalDateTime oldToTime = byId.orElseThrow().getToTime();

            Flight flight = flightMapper.toEntity(flightDtoR,byIdTo.orElseThrow(),byIdFrom.orElseThrow(),byIdAirport.orElseThrow());
            LocalDateTime toDateTime = flight.getFromTime().plusHours(hour).plusMinutes(minute).
                    atZone(ZoneId.of(flight.getFrom().getName())).toLocalDateTime();
            flight.setToTime(toDateTime);
            Flight save = flightRepository.save(flight);
            agentService.sendReportEditFlight(save,oldFromTime,oldToTime);
            FlightDtoR dto = flightMapper.toDto(save);
            log.info("{} updated",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public FlightDtoR flightsGet(UUID id) {
        try {
            Optional<Flight> byId = flightRepository.findById(id);
            Flight flight = byId.orElseThrow(RuntimeException::new);
            String gmtFrom = flight.getFrom().getGmt();
            String gmtTo = flight.getTo().getGmt();

            Optional<String> firstFrom = ZoneId.getAvailableZoneIds().stream()
                    .filter(s -> ZoneId.of(s).getRules().toString().equals(gmtFrom))
                    .findFirst();
            Optional<String> firstTo = ZoneId.getAvailableZoneIds().stream()
                    .filter(s -> ZoneId.of(s).getRules().toString().equals(gmtTo))
                    .findFirst();

            flight.setFromTime(flight.getFromTime().atZone(ZoneId.of(firstFrom.orElseThrow())).toLocalDateTime());
            flight.setToTime(flight.getToTime().atZone(ZoneId.of(firstTo.orElseThrow())).toLocalDateTime());
            System.out.println(flight);
            FlightDtoR dto = flightMapper.toDto(flight);
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
    public Page<FlightDtoR> getAllFlights(int limit, int page) {
        try {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Flight> flights = flightRepository.findAll(pageable);
            Set<String> zoneIds = ZoneId.getAvailableZoneIds();
            flights.forEach(flight -> {
                Optional<String> firstFrom = zoneIds.stream().filter(s -> ZoneId.of(s).getRules().toString()
                        .equals(flight.getFrom().getGmt())).findFirst();
                Optional<String> firstTo = zoneIds.stream().filter(s -> ZoneId.of(s).getRules().toString()
                        .equals(flight.getTo().getGmt())).findFirst();
                flight.setFromTime(flight.getFromTime().atZone(ZoneId.of(firstFrom.orElseThrow())).toLocalDateTime());
                flight.setToTime(flight.getToTime().atZone(ZoneId.of(firstTo.orElseThrow())).toLocalDateTime());
            });
            if (flights.getContent().size()<flights.getSize()) {
                List<Flight> all1 = flightRepository.findAll();
                Page<Flight> empty = new PageImpl<>(all1);
                Page<FlightDtoR> dtoPage = flightMapper.toDtoPage(empty);
                log.info("{} gave",empty);
//                dtoPage.forEach(this::setNull);
                return dtoPage;
            }
            Page<FlightDtoR> dtoPage = flightMapper.toDtoPage(flights);
            log.info("{} gave",dtoPage);
//            dtoPage.forEach(this::setNull);
            return dtoPage;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    private void setNull(FlightDto dto) {
        dto.getAirport().setFlights(null);
        dto.getAirport().setCompany(null);
        dto.getAirport().setCities(null);

        dto.getAuthUsers().forEach(authUser -> {
            authUser.setCompany(null);
            authUser.setActivateCodes(null);
            authUser.setFlights(null);
        });

        dto.setTo(null);

        dto.setFrom(null);
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
