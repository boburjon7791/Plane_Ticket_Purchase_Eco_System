package com.example.demo.dtoRequest;


import com.example.demo.entities.Airport;
import com.example.demo.entities.AuthUser;
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
public class CompanyDtoR {
    public UUID id;
    @NotBlank
    public String name;
    @Builder.Default
    public Set<Airport> airports=new HashSet<>();
    @Builder.Default
    public Set<AuthUser> agents = new HashSet<>();
}
