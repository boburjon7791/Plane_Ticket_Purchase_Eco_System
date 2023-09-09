package com.example.demo.controllers;

import com.example.demo.dto.CityDto;
import com.example.demo.dtoRequest.CityDtoR;
import com.example.demo.services.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.city")
@PreAuthorize("hasRole('ADMIN')")
public class CityController {
    public final CityService cityService;
    @PostMapping("/create")
    public ResponseEntity<UUID> create(@Valid @RequestBody CityDtoR cityDtoR){
        CityDtoR create = cityService.cityCreate(cityDtoR);
        return new ResponseEntity<>(create.getId(), HttpStatus.CREATED);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get/all")
    public Page<CityDtoR> getAll(@RequestParam Map<String,String> param){
        String Page = param.getOrDefault("page", "0");
        String Size = param.getOrDefault("size", "5");
        int page = Integer.parseInt(Page);
        int size = Integer.parseInt(Size);
        return cityService.getAllCities(page, size);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get/{name}")
    @Cacheable(key = "#name",value = "cities")
    public ResponseEntity<CityDtoR> getCity(@PathVariable String name){
        CityDtoR cityDto = cityService.cityRead(name);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-id/{id}")
    public ResponseEntity<CityDtoR> getCityId(@PathVariable String id){
        CityDtoR cityDto = cityService.cityRead(UUID.fromString(id));
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @PutMapping("/update/{name}")
    @CachePut(key = "#name",value = "cities")
    @Transactional
    public ResponseEntity<UUID> updateCity(@PathVariable String name, @Valid @RequestBody CityDtoR cityDtoR){
        cityDtoR.setName(name);
        CityDtoR cityEdit = cityService.cityEdit(cityDtoR);
        return new ResponseEntity<>(cityEdit.getId(),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{name}")
    @Transactional
    @CacheEvict(key = "#name",value = "cities")
    public void deleteCity(@PathVariable String name){
        cityService.cityDelete(name);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/zones")
    public Set<String> zoneRules(){
        return Set.of("ZoneRules[currentStandardOffset=+01:00]",
                "ZoneRules[currentStandardOffset=+02:00]",
                "ZoneRules[currentStandardOffset=+03:00]",
                "ZoneRules[currentStandardOffset=+03:30]",
                "ZoneRules[currentStandardOffset=+04:00]",
                "ZoneRules[currentStandardOffset=+04:30]",
                "ZoneRules[currentStandardOffset=+05:00]",
                "ZoneRules[currentStandardOffset=+05:30]",
                "ZoneRules[currentStandardOffset=+05:45]",
                "ZoneRules[currentStandardOffset=+06:00]",
                "ZoneRules[currentStandardOffset=+06:30]",
                "ZoneRules[currentStandardOffset=+07:00]",
                "ZoneRules[currentStandardOffset=+08:00]",
                "ZoneRules[currentStandardOffset=+08:45]",
                "ZoneRules[currentStandardOffset=+09:00]",
                "ZoneRules[currentStandardOffset=+09:30]",
                "ZoneRules[currentStandardOffset=+10:00]",
                "ZoneRules[currentStandardOffset=+10:30]",
                "ZoneRules[currentStandardOffset=+11:00]",
                "ZoneRules[currentStandardOffset=+12:00]",
                "ZoneRules[currentStandardOffset=+12:45]",
                "ZoneRules[currentStandardOffset=+13:00]",
                "ZoneRules[currentStandardOffset=+14:00]",
                "ZoneRules[currentStandardOffset=-01:00]",
                "ZoneRules[currentStandardOffset=-02:00]",
                "ZoneRules[currentStandardOffset=-03:00]",
                "ZoneRules[currentStandardOffset=-03:30]",
                "ZoneRules[currentStandardOffset=-04:00]",
                "ZoneRules[currentStandardOffset=-05:00]",
                "ZoneRules[currentStandardOffset=-06:00]",
                "ZoneRules[currentStandardOffset=-07:00]",
                "ZoneRules[currentStandardOffset=-08:00]",
                "ZoneRules[currentStandardOffset=-09:00]",
                "ZoneRules[currentStandardOffset=-09:30]",
                "ZoneRules[currentStandardOffset=-10:00]",
                "ZoneRules[currentStandardOffset=-11:00]",
                "ZoneRules[currentStandardOffset=-12:00]",
                "ZoneRules[currentStandardOffset=Z]"
                );
    }
}
