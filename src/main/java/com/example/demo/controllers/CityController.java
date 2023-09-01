package com.example.demo.controllers;

import com.example.demo.dto.CityDto;
import com.example.demo.services.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.city")
@PreAuthorize("hasRole('ADMIN')")
public class CityController {
    public final CityService cityService;
    @PostMapping("/create")
    public ResponseEntity<CityDto> create(@Valid @RequestBody CityDto cityDto){
        CityDto create = cityService.cityCreate(cityDto);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get/all")
    public Page<CityDto> getAll(@RequestParam Map<String,String> param){
        String Page = param.getOrDefault("page", "0");
        String Size = param.getOrDefault("size", "5");
        int page = Integer.parseInt(Page);
        int size = Integer.parseInt(Size);
        return cityService.getAllCities(page, size);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get/{name}")
    public ResponseEntity<CityDto> getCity(@PathVariable String name){
        CityDto cityDto = cityService.cityRead(name);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @PutMapping("/update/{name}")
    @Transactional
    public ResponseEntity<CityDto> updateCity(@PathVariable String name,@Valid @RequestBody CityDto cityDto){
        cityDto.setName(name);
        CityDto cityEdit = cityService.cityEdit(cityDto);
        return new ResponseEntity<>(cityEdit,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{name}")
    @Transactional
    public void deleteCity(@PathVariable String name){
        cityService.cityDelete(name);
    }
}
