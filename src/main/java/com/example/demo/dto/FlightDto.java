package com.example.demo.dto;

import com.example.demo.entities.Airport;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.City;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
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


    private LocalDateTime toTime;

    @NotNull
    public Airport airport;

    @NotNull
    public Set<AuthUser> authUsers;

    @NotNull
    private City from;

    @NotNull
    private City to;
}
