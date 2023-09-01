package com.example.demo.controllers;

import com.example.demo.dto.FlightDto;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.services.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<FlightDto> createFlight(@RequestBody @Valid FlightDto flightDto,@RequestParam Map<String,String> param){
        FlightDto create = flightService.flightCreate(flightDto,param);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FlightDto> getFlight(@PathVariable UUID id){
        FlightDto flightDto = flightService.flightsGet(id);
        return new ResponseEntity<>(flightDto,HttpStatus.OK);
    }
    @GetMapping("/get/all")
    @PreAuthorize("isAuthenticated()")
    public Page<FlightDto> getAll(@RequestParam Map<String,String> param){
        int size= Integer.parseInt(param.getOrDefault("size","5"));
        int page= Integer.parseInt(param.getOrDefault("page","0"));
        return flightService.getAllFlights(size, page);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable UUID id,
                                                  @Valid @RequestBody FlightDto flightDto,
                                                  @RequestParam Map<String, String> param){
        flightDto.setId(id);
        FlightDto edit = flightService.flightEdit(flightDto,param);
        return new ResponseEntity<>(edit,HttpStatus.OK);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/reserve/{id}")
    public void reserveFlight(@PathVariable UUID id,
                                                  @Valid @RequestBody FlightDto flightDto,
                                                  @RequestParam UUID userID){
        flightDto.setId(id);
        flightService.flightReserve(flightDto,userID);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/cancel/{id}")
    public void cancelFlight(@PathVariable UUID id,
                                                  @Valid @RequestBody FlightDto flightDto,
                                                  @RequestParam UUID userID){
        flightDto.setId(id);
        flightService.flightCancel(flightDto,userID);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteFlight(@PathVariable UUID id){
        flightService.flightDelete(id);
    }
}
