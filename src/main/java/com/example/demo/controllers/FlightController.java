package com.example.demo.controllers;

import com.example.demo.dto.FlightDto;
import com.example.demo.dtoRequest.FlightDtoR;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.services.FlightService;
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
@RequestMapping("/api.flight")
@PreAuthorize("hasRole('AGENT')")
public class FlightController {
    public final FlightService flightService;
    private final AuthUserRepository authUserRepository;

    @PostMapping("/create")
    public ResponseEntity<UUID> createFlight(@RequestBody @Valid FlightDtoR flightDtoR, @RequestParam Map<String,String> param){
        FlightDtoR create = flightService.flightCreate(flightDtoR,param);
        return new ResponseEntity<>(create.getId(), HttpStatus.CREATED);
    }
    @GetMapping("/get-id/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FlightDtoR> getFlightId(@PathVariable String id){
        FlightDtoR flightDto = flightService.flightsGet(UUID.fromString(id));
        return new ResponseEntity<>(flightDto,HttpStatus.OK);
    }
    @GetMapping("/get/all")
    @PreAuthorize("isAuthenticated()")
    public Page<FlightDtoR> getAll(@RequestParam Map<String,String> param){
        int size= Integer.parseInt(param.getOrDefault("size","5"));
        int page= Integer.parseInt(param.getOrDefault("page","0"));
        return flightService.getAllFlights(size, page);
    }
    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<UUID> updateFlight(@PathVariable UUID id,
                                                  @Valid @RequestBody FlightDtoR flightDtoR,
                                                  @RequestParam Map<String, String> param){
        flightDtoR.setId(id);
        FlightDtoR edit = flightService.flightEdit(flightDtoR,param);
        return new ResponseEntity<>(edit.getId(),HttpStatus.OK);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/reserve/{id}")
    @Transactional
    public void reserveFlight(@PathVariable UUID id, @RequestParam UUID userID){
        flightService.flightReserve(id,userID);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/cancel/{id}")
    @Transactional
    public void cancelFlight(@PathVariable UUID id, @RequestParam UUID userID){
        flightService.flightCancel(id,userID);
    }
    @DeleteMapping("/delete/{id}")
    @Transactional
    public void deleteFlight(@PathVariable UUID id){
        flightService.flightDelete(id);
    }
}
