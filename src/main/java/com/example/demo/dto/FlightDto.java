package com.example.demo.dto;

import com.example.demo.entities.Airport;
import com.example.demo.entities.AuthUser;
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
    public Airport airport;
    @NotNull
    public Set<AuthUser> authUsers;
}
