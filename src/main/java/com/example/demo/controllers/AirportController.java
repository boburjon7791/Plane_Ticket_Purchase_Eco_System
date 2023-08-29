package com.example.demo.controllers;

import com.example.demo.dto.AirportDto;
import com.example.demo.services.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.airport")
@PreAuthorize("hasRole('ADMIN')")
public class AirportController {
    public final AirportService airportService;
    @PostMapping("/create")
    public ResponseEntity<AirportDto> createAirport(@Valid @RequestBody AirportDto airportDto){
        AirportDto airport = airportService.createAirport(airportDto);
        return new ResponseEntity<>(airport, HttpStatus.CREATED);
    }
    @GetMapping("/get/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AirportDto> getAirport(@PathVariable String name){
        AirportDto airport = airportService.getAirport(name);
        return new ResponseEntity<>(airport,HttpStatus.OK);
    }
    @GetMapping("/get-all")
    @PreAuthorize("isAuthenticated()")
    public Page<AirportDto> getAll(@RequestParam(required = false)Map<String,String> param){
        int page = Integer.parseInt(param.getOrDefault("size","5"));
        int size = Integer.parseInt(param.getOrDefault("page","0"));
        return airportService.getAll(size, page);
    }
    @PutMapping("/update/{name}")
    public ResponseEntity<AirportDto> updateAirport(@PathVariable String name,
                                                    @RequestBody AirportDto airportDto){
        airportDto.setName(name);
        AirportDto airport = airportService.updateAirport(airportDto);
        return new ResponseEntity<>(airport,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{name}")
    public void deleteAirport(@PathVariable String name){
        airportService.deleteAirport(name);
    }
}
