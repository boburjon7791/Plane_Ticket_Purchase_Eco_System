package com.example.demo.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "airport")

public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @NotBlank
    @Column(unique = true,nullable = false)
    private String name;


    @NotNull
    @ToString.Exclude
    @ManyToOne(optional = false,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;



    @Builder.Default
    @OneToMany(mappedBy = "airport",
    fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @ToString.Exclude
    @NotNull
    private Set<Flight> flights=new HashSet<>();


    @Builder.Default
    @ManyToMany(mappedBy = "airports",cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @NotNull
    private Set<City> cities = new HashSet<>();

    public void addCity(City city) {
        this.cities.add(city);
    }
    public void removeCity(City city) {
        this.cities.remove(city);
    }
    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }
    public void removeFlight(Flight flight) {
        this.flights.remove(flight);
    }
}
