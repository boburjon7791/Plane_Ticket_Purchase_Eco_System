package com.example.demo.dtoRequest;

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
public class FlightDtoR {
    public UUID id;

    @NotNull
    private LocalDateTime fromTime;


    private LocalDateTime toTime;


    @NotNull
    public UUID airportId;

    public Set<AuthUser> authUsers;

    @NotNull
    private UUID fromId;

    @NotNull
    private UUID toId;
}
