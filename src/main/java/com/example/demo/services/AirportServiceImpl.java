package com.example.demo.services;

import com.example.demo.dto.AirportDto;
import com.example.demo.dtoRequest.AirportDtoR;
import com.example.demo.entities.Airport;
import com.example.demo.entities.Company;
import com.example.demo.mappers.AirportMapper;
import com.example.demo.repositories.AirportRepository;
import com.example.demo.repositories.CompanyRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final CompanyRepository companyRepository;
    public final AirportRepository airportRepository;
    public final AirportMapper airportMapper;
    @Override
    public AirportDtoR createAirport(AirportDtoR airportDtor, HttpServletResponse res) {
        try {
            UUID companyId = airportDtor.getCompanyId();
            Optional<Company> byId = companyRepository.findById(companyId);
            Company company = byId.orElse(null);
            Airport airport = airportMapper.toEntity(airportDtor,company);
            Airport save = airportRepository.save(airport);
            AirportDtoR dto = airportMapper.toDto(save,save.getCompany());
            res.setStatus(201);
            log.info("{} saved",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public AirportDtoR getAirport(String name) {
        try {
            Airport airport = airportRepository.findByName(name);
            AirportDtoR dto = airportMapper.toDto(airport, airport.getCompany());
            log.info("{} gave",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public AirportDtoR updateAirport(AirportDtoR airportDtor) {
        try {
            UUID companyId = airportDtor.getCompanyId();
            Optional<Company> byId = companyRepository.findById(companyId);
            Airport airport = airportMapper.toEntity(airportDtor,byId.orElseThrow());
            Airport save = airportRepository.save(airport);
            AirportDtoR dto = airportMapper.toDto(save, save.getCompany());
            log.info("{} updated",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public void deleteAirport(String name) {
        try {
            airportRepository.deleteByName(name);
            log.info("{} airport deleted",name);
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public Page<AirportDtoR> getAll(int size, int page) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Airport> all = airportRepository.findAll(pageable);
            if (all.getContent().size()<all.getSize()) {
                List<Airport> all1 = airportRepository.findAll();
                Page<Airport> empty = new PageImpl<>(all1);
                Page<AirportDtoR> dtoPage = airportMapper.toDtoPage(empty);
                log.info("{} gave",empty);
                return dtoPage;
            }
            Page<AirportDtoR> dtoPage = airportMapper.toDtoPage(all);
            log.info("gave all");
//            dtoPage.forEach(this::setNull);
            return dtoPage;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public AirportDtoR getAirport(UUID id) {
        try {
            Optional<Airport> byId = airportRepository.findById(id);
            AirportDtoR dto = airportMapper.toDto(byId.orElseThrow(), byId.orElseThrow().getCompany());
            log.info("{} gave",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}",Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    private void setNull(AirportDto dto) {
        dto.getCompany().setAirports(null);
        dto.getCompany().setAgent(null);

        dto.getCities().forEach(city -> {
            city.setAirports(null);
            city.setFlights(null);
            city.setFlights2(null);
        });

        dto.getFlights().forEach(flight -> {
            flight.setAirport(null);
            flight.setAuthUsers(null);
            flight.setTo(null);
            flight.setFrom(null);
        });
    }
}
