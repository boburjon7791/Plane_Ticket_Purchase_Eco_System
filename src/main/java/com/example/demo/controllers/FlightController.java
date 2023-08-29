package com.example.demo.controllers;

import com.example.demo.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.flight")
public class FlightController {
    public final FlightService flightService;
}
