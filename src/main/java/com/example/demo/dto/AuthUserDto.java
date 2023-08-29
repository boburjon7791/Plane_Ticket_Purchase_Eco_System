package com.example.demo.dto;

import com.example.demo.entities.Company;
import com.example.demo.entities.Flight;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AuthUserDto {
    public UUID id;
    @Email
    @NotBlank
    public String email;
    public Company company=null;
    public Flight flight=null;
    @NotBlank
    @Size(min = 8)
    public String password;
    @NotBlank
    public String firstName;
    @NotBlank
    public String lastName;
}
