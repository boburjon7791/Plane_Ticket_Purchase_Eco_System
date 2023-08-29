package com.example.demo.controllers;

import com.example.demo.services.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.airport")
public class AirportController {
    public final AirportService airportService;
}
