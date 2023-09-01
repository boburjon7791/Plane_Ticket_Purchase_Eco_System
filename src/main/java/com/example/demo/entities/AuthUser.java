package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@Table(name = "auth_user")
public class AuthUser extends Auditable{
    @ManyToMany(mappedBy = "authUsers",cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    @Builder.Default
    private Set<Flight> flights=new HashSet<>();

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
}
