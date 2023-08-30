package com.example.demo.services;


import com.example.demo.dto.AuthUserDto;
import com.example.demo.entities.AuthUser;
import com.example.demo.mappers.AuthUserMapper;
import com.example.demo.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final AuthUserRepository authUserRepository;
    public final AuthUserMapper authUserMapper;
    @Override
    public AuthUserDto updateAuthUser(AuthUserDto authUserDto) {
        try {
            AuthUser authUser = authUserMapper.toEntity(authUserDto);
            AuthUser saved = authUserRepository.save(authUser);
            AuthUserDto dto = authUserMapper.toDto(saved);
            log.info("{} updated",dto);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            log.info("{}", Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
