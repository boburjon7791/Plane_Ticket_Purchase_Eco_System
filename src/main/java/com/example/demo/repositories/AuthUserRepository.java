package com.example.demo.repositories;

import com.example.demo.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID>, JpaSpecificationExecutor<AuthUser> {
   @Query(value = "from AuthUser where email=?1")
   AuthUser findByEmailSpecial(String email);
   AuthUser findByEmailAndBlockedFalse(String email);

   @Modifying
   @Query(value = "update AuthUser set blocked=true where email=?1")
   void blockUserById(String email);

   @Modifying
   @Query(value = "update AuthUser set blocked=false where email=?1")
   void unblockUserById(String email);

   @Modifying()
   @Query(nativeQuery = true,value = "update system_of_airline.auth_user set role='AGENT',company_id=?1 where email=?2")
   void setAgentRole(UUID companyId,String email);

   @Modifying
   @Query(value = "update AuthUser set role=Role.ADMIN where email=?1")
   void setAdminRole(String email);

   @Modifying
   @Query(value = "update AuthUser set role=Role.CUSTOMER where email=?1")
   void removeAdminRole(String email);

   @Modifying
   @Query(value = "update AuthUser set role=Role.CUSTOMER where email=?1")
   void removeAgentRole(String email);
}