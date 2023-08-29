package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FlightDto {
    public UUID id;
    @NotNull
    public LocalDateTime localDateTime;
    @NotNull
    public AirportDto airport;
    @NotNull
    public Set<AuthUserDto> authUsers;
}
