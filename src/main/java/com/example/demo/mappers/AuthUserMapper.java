package com.example.demo.mappers;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.entities.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.LinkedList;
import java.util.List;
@Mapper
public interface AuthUserMapper {
   AuthUserMapper AUTH_USER_MAPPER = Mappers.getMapper(AuthUserMapper.class);
   AuthUser toEntity(AuthUserDto authUserDto);
   AuthUserDto toDto(AuthUser authUser);

   default Page<AuthUserDto> toDtoPage(Page<AuthUser> authUsers){
      if (authUsers==null || authUsers.isEmpty()) {
         return null;
      }
      return authUsers.map(AUTH_USER_MAPPER::toDto);
   }
}
