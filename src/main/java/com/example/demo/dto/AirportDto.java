package com.example.demo.dto;


import com.example.demo.entities.City;
import com.example.demo.entities.Company;
import com.example.demo.entities.Flight;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AirportDto {
     public UUID id;
     @NotBlank
     public String name;
     @NotNull
     public CompanyDto company;
     @Builder.Default
     public Set<FlightDto> flights=new HashSet<>();
     @Builder.Default
     public Set<CityDto> cities=new HashSet<>();
}
