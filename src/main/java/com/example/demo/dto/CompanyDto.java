package com.example.demo.dto;


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
public class CompanyDto {
    public UUID id;
    @NotBlank
    public String name;
    @Builder.Default
    public Set<AirportDto> airports=new HashSet<>();
    @Builder.Default
    public Set<AuthUserDto> agent = new HashSet<>();
}
