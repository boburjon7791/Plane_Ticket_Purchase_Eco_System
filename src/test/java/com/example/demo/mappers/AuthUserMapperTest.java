package com.example.demo.mappers;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Company;
import com.example.demo.entities.Flight;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthUserMapperTest {

    @Test
    void toEntity() {
        AuthUserDto dto = AuthUserDto.builder()
                .id(UUID.randomUUID())
                .email("salom@mail.com")
                .firstName("salom")
                .lastName("hayr")
                .password("123456789")
                .build();
        System.out.println("dto = " + dto);
        AuthUserMapper mapper = Mappers.getMapper(AuthUserMapper.class);
        AuthUser authUser = mapper.toEntity(dto);
        System.out.println("authUser = " + authUser);
    }

    @Test
    void toDto() {
        AuthUser user = AuthUser.builder()
                .id(UUID.randomUUID())
                .firstName("salom")
                .lastName("xayr")
                .password("11223344")
                .build();
        System.out.println("user = " + user);
        AuthUserMapper mapper = Mappers.getMapper(AuthUserMapper.class);
        AuthUserDto dto = mapper.toDto(user);
        System.out.println("dto = " + dto);
    }
}