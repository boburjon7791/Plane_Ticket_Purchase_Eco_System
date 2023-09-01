package com.example.demo.dtoRequest;

import com.example.demo.entities.AuthUser;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class FlightDtoR {
    public UUID id;

    @NotNull
    private LocalDateTime fromTime;


    public LocalDateTime toTime;


    @NotNull
    public UUID airportId;

    public Set<AuthUser> authUsers;

    @NotNull
    public UUID fromId;

    @NotNull
    public UUID toId;

    @NotNull
    @Positive
    public Double price;
}
