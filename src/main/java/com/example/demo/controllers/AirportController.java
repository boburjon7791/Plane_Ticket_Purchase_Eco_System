package com.example.demo.controllers;

import com.example.demo.dto.AirportDto;
import com.example.demo.dtoRequest.AirportDtoR;
import com.example.demo.services.AirportService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.airport")
@PreAuthorize("hasRole('ADMIN')")
public class AirportController {
    public final AirportService airportService;
    @PostMapping("/create")
    public ResponseEntity<UUID> createAirport(@Valid @RequestBody AirportDtoR airportDtoR, HttpServletResponse res){
        AirportDtoR airport = airportService.createAirport(airportDtoR,res);
        return new ResponseEntity<>(airport.getId(),HttpStatus.CREATED);
    }
    @GetMapping("/get/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AirportDtoR> getAirport(@PathVariable String name){
        AirportDtoR airport = airportService.getAirport(name);
        return new ResponseEntity<>(airport,HttpStatus.OK);
    }
    @GetMapping("/get-id/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AirportDtoR> getAirportId(@PathVariable String id){
        AirportDtoR airport = airportService.getAirport(UUID.fromString(id));
        return new ResponseEntity<>(airport,HttpStatus.OK);
    }
    @GetMapping("/get/all")
    @PreAuthorize("isAuthenticated()")
    public Page<AirportDtoR> getAll(@RequestParam Map<String,String> param){
        int page = Integer.parseInt(param.getOrDefault("size","5"));
        int size = Integer.parseInt(param.getOrDefault("page","0"));
        return airportService.getAll(size, page);
    }
    @PutMapping("/update/{name}")
    @Transactional
    public ResponseEntity<UUID> updateAirport(@PathVariable String name,
                                                    @RequestBody @Valid AirportDtoR airportDtor){
        airportDtor.setName(name);
        AirportDtoR airport = airportService.updateAirport(airportDtor);
        return new ResponseEntity<>(airport.getId(),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{name}")
    @Transactional
    public void deleteAirport(@PathVariable String name){
        airportService.deleteAirport(name);
    }
}
