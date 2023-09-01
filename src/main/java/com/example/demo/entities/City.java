package com.example.demo.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZoneId;
import java.time.zone.ZoneRules;
import java.util.*;

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

//    @NotNull
//    private String zone;

    @NotNull
    @Builder.Default
    @OneToMany(mappedBy = "from"
            ,cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    Set<Flight> flights = new HashSet<>();

    @NotBlank
    private String gmt;

    @NotNull
    @Builder.Default
    @OneToMany(mappedBy = "to",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    Set<Flight> flights2 = new HashSet<>();
    public void addAirports(Airport airport) {
        this.airports.add(airport);
    }
    public void removeAirports(Airport airport) {
        this.airports.remove(airport);
    }
}
