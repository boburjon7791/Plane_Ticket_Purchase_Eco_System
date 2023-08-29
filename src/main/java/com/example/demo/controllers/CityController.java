package com.example.demo.controllers;

import com.example.demo.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.city")
public class CityController {
    public final CityService cityService;
}
