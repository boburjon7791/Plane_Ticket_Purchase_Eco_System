package com.example.demo.dto;

import com.example.demo.entities.Airport;
import com.example.demo.entities.AuthUser;
import jakarta.persistence.Column;
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
    private LocalDateTime fromTime;

    @NotNull
    private LocalDateTime toTime;

    @NotNull
    public Airport airport;

    @NotNull
    public Set<AuthUser> authUsers;
}
