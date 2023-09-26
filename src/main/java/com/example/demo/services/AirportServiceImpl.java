package com.example.demo.services;

import com.example.demo.dtoRequest.AirportDtoR;
import com.example.demo.entities.Airport;
import com.example.demo.entities.Company;
import com.example.demo.exceptions.ForbiddenAccessException;
import com.example.demo.exceptions.NotFoundException;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final CompanyRepository companyRepository;
    public final AirportRepository airportRepository;
    public final AirportMapper airportMapper;
    @Override
    public AirportDtoR createAirport(AirportDtoR airportDtor, HttpServletResponse res) {
            if (airportRepository.existsAirportByName(airportDtor.name)) {
                throw new ForbiddenAccessException();
            }
            UUID companyId = airportDtor.getCompanyId();
            Optional<Company> byId = companyRepository.findById(companyId);
            Company company = byId.orElse(null);
            Airport airport = airportMapper.toEntity(airportDtor,company);
            Airport save = airportRepository.save(airport);
            AirportDtoR dto = airportMapper.toDto(save,save.getCompany());
            res.setStatus(201);
            log.info("{} saved",dto);
            return dto;
    }

    @Override
    public AirportDtoR getAirport(String name) {
            Airport airport = airportRepository.findByName(name);
            AirportDtoR dto = airportMapper.toDto(airport, airport.getCompany());
            log.info("{} gave",dto);
            return dto;
    }

    @Override
    public AirportDtoR updateAirport(AirportDtoR airportDtor) {
            UUID companyId = airportDtor.getCompanyId();
            Optional<Company> byId = companyRepository.findById(companyId);
            Airport airport = airportMapper.toEntity(airportDtor,byId.orElseThrow(NotFoundException::new));
            Airport airport1 = airportRepository.findById(airport.getId()).orElseThrow(NotFoundException::new);
            if (!airport1.getCompany().getId().equals(airport.getCompany().getId())) {
                throw new ForbiddenAccessException();
            }
            if (!airport1.getFlights().equals(airport.getFlights())) {
                throw new ForbiddenAccessException();
            }
            if (!airport.getCities().equals(airport1.getCities())) {
                throw new ForbiddenAccessException();
            }
            Airport save = airportRepository.save(airport);
            AirportDtoR dto = airportMapper.toDto(save, save.getCompany());
            log.info("{} updated",dto);
            return dto;
    }

    @Override
    public Page<AirportDtoR> getAll(int size, int page) {
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
            return dtoPage;
    }

    @Override
    public AirportDtoR getAirport(UUID id) {
            Optional<Airport> byId = airportRepository.findById(id);
            AirportDtoR dto = airportMapper.toDto(byId.orElseThrow(NotFoundException::new),
                    byId.orElseThrow(NotFoundException::new).getCompany());
            log.info("{} gave",dto);
            return dto;
    }
}
