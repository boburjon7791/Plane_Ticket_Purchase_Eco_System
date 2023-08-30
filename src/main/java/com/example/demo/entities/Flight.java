package com.example.demo.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
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
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private LocalDateTime localDateTime;

    @NotNull
    @ToString.Exclude
    @JoinColumn(name = "airport_id")
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,
    optional = false)
    private Airport airport;

    @NotNull
    @OneToMany(mappedBy = "flight",cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private Set<AuthUser> authUsers=new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @NotNull
    private Set<City> from = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<City> to = new HashSet<>();
}
