package com.example.demo.services;


import com.example.demo.dto.AuthUserDto;
import com.example.demo.dtoRequest.AuthUserDtoR;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Company;
import com.example.demo.mappers.AuthUserMapper;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CompanyRepository companyRepository;
    public final AuthUserRepository authUserRepository;
    public final AuthUserMapper authUserMapper;
    @Override
    public AuthUserDtoR updateAuthUser(AuthUserDtoR authUserDtoR) {
        try {
            UUID companyId = authUserDtoR.getCompanyId();
            Optional<Company> byId = companyRepository.findById(companyId);
            AuthUser authUser = authUserMapper.toEntity(authUserDtoR,byId.orElseThrow());
            AuthUser saved = authUserRepository.save(authUser);
            AuthUserDtoR dto = authUserMapper.toDto(saved);
            log.info("{} updated",dto);
//            setNull(dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    private void setNull(AuthUserDto dto) {
        dto.getCompany().setAgent(null);
        dto.getCompany().setAirports(null);

        dto.getFlight().setFrom(null);

        dto.getFlight().setTo(null);
    }
}
