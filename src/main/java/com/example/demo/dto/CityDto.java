package com.example.demo.dto;


import com.example.demo.entities.Airport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CityDto {
    public UUID id;
    @NotBlank
    public String name;
    @Builder.Default
    public Set<Airport> airports = new HashSet<>();
}
