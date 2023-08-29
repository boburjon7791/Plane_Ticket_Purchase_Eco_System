package com.example.demo.entities;
import jakarta.persistence.*;
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
    @Builder.Default
    private LocalDateTime localDateTime=LocalDateTime.now();

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
}
