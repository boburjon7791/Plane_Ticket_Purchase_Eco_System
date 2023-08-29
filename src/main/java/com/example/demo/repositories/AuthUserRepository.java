package com.example.demo.repositories;

import com.example.demo.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID>, JpaSpecificationExecutor<AuthUser> {
   AuthUser findByEmailAndBlockedFalse(String email);

   @Query(value = "")
   void activateUser(Integer code, Long id);
}