package com.example.demo.dtoRequest;

import com.example.demo.entities.ActivateCodes;
import com.example.demo.entities.Auditable;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Flight;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class AuthUserDtoR {
    public UUID id;
    @Email
    @NotBlank
    public String email;
    @Builder.Default
    public UUID companyId=null;
    @Builder.Default
    public UUID flightId=null;
    @NotBlank
    @Size(min = 8)
    public String password;
    @NotBlank
    public String firstName;
    @NotBlank
    public String lastName;
    @NotBlank
    @Builder.Default
    public String role=Auditable.Role.CUSTOMER.name();
    @NotNull
    @Builder.Default
    public Boolean blocked=true;
    @NotNull
    @Builder.Default
    public Set<Flight> flights = new HashSet<>();
    @Builder.Default
    public Set<ActivateCodes> activateCodes = new HashSet<>();
}
