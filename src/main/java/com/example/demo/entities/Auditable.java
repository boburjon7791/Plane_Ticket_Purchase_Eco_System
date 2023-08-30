package com.example.demo.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@ToString
public class Auditable {
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     protected UUID id;


     @Column(name = "first_name",nullable = false)
     @NotBlank
     protected String firstName;

     @NotBlank
     @Column(nullable = false,name = "last_name")
     protected String lastName;


     @Email
     @NotBlank
     @Column(nullable = false,unique = true)
     protected String email;

     @NotNull
     @Builder.Default
     @Enumerated(EnumType.STRING)
     protected Role role=Role.CUSTOMER;

     @NotNull
     @Column(nullable = false)
     @Builder.Default
     protected Boolean blocked=true;

     @NotBlank
     @Column(nullable = false)
     @Size(min = 8)
     protected String password;

     public enum Role{
         ADMIN,
         CUSTOMER,
         AGENT
     }
}
