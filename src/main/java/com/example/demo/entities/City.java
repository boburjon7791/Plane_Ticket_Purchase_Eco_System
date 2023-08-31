package com.example.demo.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false,unique = true)
    private String name;


    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
    fetch = FetchType.LAZY)
    @JoinTable(name = "city_airport",
    joinColumns = @JoinColumn(name = "city_id"),
    inverseJoinColumns = @JoinColumn(name = "airport_id"))
    @Builder.Default
    @NotNull
    private Set<Airport> airports=new HashSet<>();

    @NotNull
    @Builder.Default
    @OneToMany(mappedBy = "from"
            ,cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    Set<Flight> flights = new HashSet<>();

    @NotNull
    @Builder.Default
    @OneToMany(mappedBy = "to",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    Set<Flight> flights2 = new HashSet<>();

    public void addAirports(Airport airport) {
        this.airports.add(airport);
    }
    public void removeAirports(Airport airport) {
        this.airports.remove(airport);
    }
}
