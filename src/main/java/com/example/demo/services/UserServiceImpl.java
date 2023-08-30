package com.example.demo.services;


import com.example.demo.dto.AuthUserDto;
import com.example.demo.entities.AuthUser;
import com.example.demo.mappers.AuthUserMapper;
import com.example.demo.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final AuthUserRepository authUserRepository;
    public final AuthUserMapper authUserMapper=AuthUserMapper.AUTH_USER_MAPPER;
    @Override
    public AuthUserDto updateAuthUser(AuthUserDto authUserDto) {
        try {
            AuthUser authUser = authUserMapper.toEntity(authUserDto);
            AuthUser saved = authUserRepository.save(authUser);
            return authUserMapper.toDto(saved);
        }catch (Exception e){
            e.printStackTrace();
            // TODO: 30/08/2023 log
            return null;
        }
    }
}
