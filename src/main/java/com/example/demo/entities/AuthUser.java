package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "auth_user")
public class AuthUser extends Auditable{
    @ManyToOne(fetch = FetchType.LAZY
    ,cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id")
    @ToString.Exclude
    @Builder.Default
    private Flight flight=null;

    @OneToMany(mappedBy = "authUser",cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    protected Set<ActivateCodes> activateCodes=new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @ToString.Exclude
    @Builder.Default
    private Company company=null;

    @Builder
    public AuthUser(UUID id, @NotBlank String firstName, @NotBlank String lastName,
                    @Email @NotBlank String email, @NotNull Role role,
                    @NotNull Boolean blocked, @NotBlank @Size(min = 8) String password,
                    Flight flight, Set<ActivateCodes> activateCodes, Company company) {
        super(id, firstName, lastName, email, role, blocked, password);
        this.flight = flight;
        this.activateCodes = activateCodes;
        this.company = company;
    }
}
