package com.example.demo.mappers;

import com.example.demo.dto.AuthUserDto;
import com.example.demo.entities.AuthUser;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
@Mapper(componentModel = "spring")
@Component
public interface AuthUserMapper {
//   AuthUserMapper AUTH_USER_MAPPER = Mappers.getMapper(AuthUserMapper.class);
   AuthUser toEntity(AuthUserDto authUserDto);
   AuthUserDto toDto(AuthUser authUser);
   AuthUser toEntity(AuthUserDto authUserDto, CycleAvoidingMappingContext context);
   AuthUserDto toDto(AuthUser authUser, CycleAvoidingMappingContext context);

   @BeforeMapping
   default void handleAuthUserDto(AuthUser authUser, @MappingTarget AuthUserDto authUserDto, @Context CycleAvoidingMappingContext context) {

      if (authUser == null) {
         return;
      }
      context.storeMappedInstance(authUser, authUserDto);
   }

   // Context parametrini delegat qilamiz
   @BeforeMapping
   default void handleAuthUser(AuthUserDto authUserDto, @MappingTarget AuthUser authUser, @Context CycleAvoidingMappingContext context) {
      if (authUserDto == null) {
         return;
      }
      context.storeMappedInstance(authUserDto, authUser);
   }

   default Page<AuthUserDto> toDtoPage(Page<AuthUser> authUsers){
      if (authUsers==null || authUsers.isEmpty()) {
         return null;
      }
      return authUsers.map(this::toDto);
   }
}
