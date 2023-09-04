package com.example.demo.repositories;

import com.example.demo.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID>, JpaSpecificationExecutor<AuthUser> {
   @Query(nativeQuery = true, value = "select * from system_of_airline.auth_user au where au.email=?1")
   AuthUser findByEmailSpecial(String email);
   AuthUser findByEmailAndBlockedFalse(String email);

   @Modifying
   @Query(value = "update AuthUser au set au.blocked=true where au.email=?1")
   void blockUserById(String email);

   @Modifying
   @Query(value = "update AuthUser au set au.blocked=false where au.email=?1")
   void unblockUserById(String email);

   @Modifying()
   @Query(nativeQuery = true,value = "update system_of_airline.auth_user au set au.role='AGENT',au.company_id=?2 where au.email=?1")
   void setAgentRole(String email, String companyId);

   @Modifying
   @Query(value = "update AuthUser au set au.role='ADMIN' where au.email=?1")
   void setAdminRole(String email);

   @Modifying
   @Query(value = "update AuthUser au set au.role='CUSTOMER' where au.email=?1")
   void removeAdminRole(String email);

   @Modifying
   @Query(value = "update AuthUser au set au.role='CUSTOMER' where au.email=?1")
   void removeAgentRole(String email);
}