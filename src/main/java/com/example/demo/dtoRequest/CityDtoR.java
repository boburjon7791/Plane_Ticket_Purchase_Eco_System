package com.example.demo.dtoRequest;


import com.example.demo.entities.Airport;
import com.example.demo.entities.Flight;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CityDtoR {
    public UUID id;
    @NotBlank
    public String name;

    public String gmt;

    @Builder.Default
    public Set<Airport> airports = new HashSet<>();
    @Builder.Default
    public Set<Flight> flights = new HashSet<>();
    @Builder.Default
    public Set<Flight> flights2 = new HashSet<>();
}
