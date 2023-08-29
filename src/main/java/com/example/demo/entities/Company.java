package com.example.demo.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false,unique = true)
    private String name;


    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @NotNull
    private Set<AuthUser> agent=new HashSet<>();

    public void addAgent(AuthUser agent) {
        if (agent.getRole().name().equals("AGENT")) {
            this.agent.add(agent);
        }
    }
    public void remove(AuthUser agent){
        this.agent.remove(agent);
    }
}
