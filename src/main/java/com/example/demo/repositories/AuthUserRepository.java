package com.example.demo.repositories;

import com.example.demo.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID>, JpaSpecificationExecutor<AuthUser> {
   AuthUser findByEmailAndBlockedFalse(String email);

   @Modifying
   @Query(value = "update AuthUser au set au.blocked=true where au.email=?1")
   void blockUserById(String email);

   @Modifying
   @Query(value = "update AuthUser au set au.blocked=false where au.email=?1")
   void unblockUserById(String email);
}