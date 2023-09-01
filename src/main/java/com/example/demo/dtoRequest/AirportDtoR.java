package com.example.demo.dtoRequest;


import com.example.demo.entities.City;
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
public class AirportDtoR {
     public UUID id;
     @NotBlank
     public String name;
     @NotNull
     public UUID companyId;

     @Builder.Default
     public Set<City> cities=new HashSet<>();
     @Builder.Default
     public Set<Flight> flights=new HashSet<>();
}
