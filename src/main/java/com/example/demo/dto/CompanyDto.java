package com.example.demo.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CompanyDto {
    public UUID id;
    @NotBlank
    public String name;
    public Set<AuthUserDto> agent = new HashSet<>();
}
