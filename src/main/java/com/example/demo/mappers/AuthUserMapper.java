package com.example.demo.mappers;

import com.example.demo.dtoRequest.AuthUserDtoR;
import com.example.demo.entities.Auditable;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Mapper(componentModel = "spring")
@Component
public interface AuthUserMapper {
   AuthUserMapper AUTH_USER_MAPPER = Mappers.getMapper(AuthUserMapper.class);
   default AuthUser toEntity(AuthUserDtoR authUserDtoR, Company company){
      return AuthUser.builder()
              .id(authUserDtoR.getId())
              .password(authUserDtoR.password)
              .role(Auditable.Role.valueOf(authUserDtoR.getRole()))
              .lastName(authUserDtoR.lastName)
              .firstName(authUserDtoR.firstName)
              .blocked(authUserDtoR.blocked)
              .email(authUserDtoR.email)
              .activateCodes(authUserDtoR.getActivateCodes())
              .company(company)
              .build();
   }
   default AuthUserDtoR toDto(AuthUser authUser){
      UUID companyId = null;
      if (authUser.getCompany() != null) {
         companyId = authUser.getCompany().getId();
      }
      return AuthUserDtoR.builder()
              .id(authUser.getId())
              .companyId(companyId)
              .blocked(authUser.getBlocked())
              .email(authUser.getEmail())
              .firstName(authUser.getFirstName())
              .lastName(authUser.getLastName())
              .role(authUser.getRole().name())
              .password(authUser.getPassword())
              .build();
   }

   default Page<AuthUserDtoR> toDtoPage(Page<AuthUser> authUsers){
      if (authUsers==null || authUsers.isEmpty()) {
         return null;
      }
      return authUsers.map(this::toDto);
   }
}
