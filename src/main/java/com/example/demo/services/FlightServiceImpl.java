package com.example.demo.services;


import com.example.demo.dtoRequest.FlightDtoR;
import com.example.demo.entities.Airport;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.City;
import com.example.demo.entities.Flight;
import com.example.demo.exceptions.ForbiddenAccessException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.FlightMapper;
import com.example.demo.repositories.AirportRepository;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.FlightRepository;
import com.example.demo.util.BaseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
            System.out.println(flightDtoR);
            int hour;
            int minute=0;
            try {
                hour = Integer.parseInt(param.get("hour"));
                System.out.println("---------------------");
                System.out.println("hour = " + hour);
                System.out.println("---------------------");
            }catch (NumberFormatException e){
                e.printStackTrace();
                throw new IllegalArgumentException();
            }
            try {
                minute = Integer.parseInt(param.get("minute"));
            }catch (NumberFormatException ignore){}

            UUID airportId = flightDtoR.getAirportId();
            UUID fromId = flightDtoR.getFromId();
            UUID toId = flightDtoR.getToId();
            Optional<City> byIdFrom = cityRepository.findById(fromId);
            Optional<City> byIdTo = cityRepository.findById(toId);
            Optional<Airport> byIdAirport = airportRepository.findById(airportId);
            City fromCity = byIdFrom
                    .orElseThrow(NotFoundException::new);
            Airport airport = byIdAirport
                    .orElseThrow(NotFoundException::new);
            City toCity = byIdTo
                    .orElseThrow(NotFoundException::new);
            Flight flight = flightMapper.toEntity(flightDtoR, toCity, fromCity, airport);

            String fromZone = BaseUtil.gmtsMap.get(flight.getFrom().getGmt());

            String toZone = BaseUtil.gmtsMap.get(flight.getTo().getGmt());


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
            return dto;
    }

    @Override
    public FlightDtoR flightEdit(FlightDtoR flightDtoR, Map<String, String> param) {
            int hour;
            int minute=0;
            UUID fromId = flightDtoR.getFromId();
            UUID toId = flightDtoR.getToId();
            Optional<City> byIdFrom = cityRepository.findById(fromId);
            Optional<City> byIdTo = cityRepository.findById(toId);
            Optional<Airport> byIdAirport = airportRepository.findById(flightDtoR.getAirportId());

            try {
            hour= Integer.parseInt(param.get("hour"));
            }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            throw new IllegalArgumentException();
            }

            try {
                minute= Integer.parseInt(param.get("minute"));
            }catch (Exception ignore){}

            UUID id = flightDtoR.getId();
            Optional<Flight> byId = flightRepository.findById(id);
            LocalDateTime oldFromTime = byId
                    .orElseThrow(NotFoundException::new).getFromTime();
            LocalDateTime oldToTime = byId
                    .orElseThrow(NotFoundException::new).getToTime();

            Flight flight = flightMapper.toEntity(flightDtoR,
                    byIdTo.orElseThrow(NotFoundException::new),
                    byIdFrom.orElseThrow(NotFoundException::new),
                    byIdAirport.orElseThrow(NotFoundException::new));
            String fromZone = BaseUtil.gmtsMap.get(flight.getFrom().getGmt());

            String toZone = BaseUtil.gmtsMap.get(flight.getTo().getGmt());

            ZonedDateTime zdtFrom = flight.getFromTime().atZone(ZoneId.of(fromZone));
            ZonedDateTime zdtTo = zdtFrom.plusHours(hour).plusMinutes(minute);
            ZonedDateTime zdtToTime = zdtTo.withZoneSameInstant(ZoneId.of(toZone));

            flight.setFromTime(zdtFrom.toLocalDateTime());

            flight.setToTime(zdtToTime.toLocalDateTime());


            Flight flight1 = byId
                    .orElseThrow(NotFoundException::new);
            if (!flight1.getAuthUsers().equals(flight.getAuthUsers())) {
                throw new ForbiddenAccessException();
            }

            Flight save = flightRepository.save(flight);
            agentService.sendReportEditFlight(save,oldFromTime,oldToTime);
            FlightDtoR dto = flightMapper.toDto(save);
            log.info("{} updated",dto);
            return dto;
    }

    @Override
    public FlightDtoR flightsGet(UUID id) {
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

            flight.setFromTime(flight.getFromTime().atZone(ZoneId.of(firstFrom
                    .orElseThrow(NotFoundException::new))).toLocalDateTime());
            flight.setToTime(flight.getToTime().atZone(ZoneId.of(firstTo
                    .orElseThrow(NotFoundException::new))).toLocalDateTime());
            System.out.println(flight);
            FlightDtoR dto = flightMapper.toDto(flight);
            log.info("{} gave",dto);
            return dto;
    }

    @Override
    public List<FlightDtoR> flightsGet(LocalDate time) {
            List<Flight> allByDate = flightRepository.findAllByDate(time);
            if (allByDate==null) {
                return new ArrayList<>();
            }
            Set<String> zoneIds = ZoneId.getAvailableZoneIds();
            allByDate.forEach(flight -> {
                Optional<String> firstFrom = zoneIds.stream().filter(s -> ZoneId.of(s).getRules().toString()
                        .equals(flight.getFrom().getGmt())).findFirst();
                Optional<String> firstTo = zoneIds.stream().filter(s -> ZoneId.of(s).getRules().toString()
                        .equals(flight.getTo().getGmt())).findFirst();
                flight.setFromTime(flight.getFromTime().atZone(ZoneId.of(firstFrom
                        .orElseThrow(NotFoundException::new))).toLocalDateTime());
                flight.setToTime(flight.getToTime().atZone(ZoneId.of(
                        firstTo.orElseThrow(NotFoundException::new))).toLocalDateTime());
            });
            List<FlightDtoR> flightDtoRList = flightMapper.toDtoPage(allByDate);
            log.info("{} gave",flightDtoRList);
            return flightDtoRList;
    }

    @Override
    public Page<FlightDtoR> getAllFlights(int limit, int page) {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Flight> flights = flightRepository.findAll(pageable);
            Set<String> zoneIds = ZoneId.getAvailableZoneIds();
            flights.forEach(flight -> {
                Optional<String> firstFrom = zoneIds.stream().filter(s -> ZoneId.of(s).getRules().toString()
                        .equals(flight.getFrom().getGmt())).findFirst();
                Optional<String> firstTo = zoneIds.stream().filter(s -> ZoneId.of(s).getRules().toString()
                        .equals(flight.getTo().getGmt())).findFirst();
                flight.setFromTime(flight.getFromTime().atZone(ZoneId.of(firstFrom
                        .orElseThrow(NotFoundException::new))).toLocalDateTime());
                flight.setToTime(flight.getToTime().atZone(ZoneId.of(
                        firstTo.orElseThrow(NotFoundException::new))).toLocalDateTime());
            });
            if (flights.getContent().size()<flights.getSize()) {
                List<Flight> all1 = flightRepository.findAll();
                Page<Flight> empty = new PageImpl<>(all1);
                Page<FlightDtoR> dtoPage = flightMapper.toDtoPage(empty);
                log.info("{} gave",empty);
                return dtoPage;
            }
            Page<FlightDtoR> dtoPage = flightMapper.toDtoPage(flights);
            log.info("{} gave",dtoPage);
            return dtoPage;
    }

    @Override
    public void flightReserve(UUID id, UUID userID) {
            Optional<AuthUser> byId = authUserRepository.findById(userID);
            AuthUser authUser = byId
                    .orElseThrow(NotFoundException::new);
            Optional<Flight> byId1 = flightRepository.findById(id);
            Flight flight = byId1
                    .orElseThrow(NotFoundException::new);
            flight.getAuthUsers().add(authUser);
            flightRepository.save(flight);
            log.info("{} user reserved {}",authUser,flight);
    }

    @Override
    public void flightCancel(UUID id, UUID userID) {
        Optional<AuthUser> byId = authUserRepository.findById(userID);
        AuthUser authUser = byId
                .orElseThrow(NotFoundException::new);
        Optional<Flight> byId1 = flightRepository.findById(id);
        Flight flight = byId1
                .orElseThrow(NotFoundException::new);
        flight.getAuthUsers().remove(authUser);
        flightRepository.save(flight);
        log.info("{} user canceled {}",authUser,flight);
    }
}
