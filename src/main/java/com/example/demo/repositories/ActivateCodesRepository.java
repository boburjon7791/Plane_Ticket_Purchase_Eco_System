package com.example.demo.repositories;

import com.example.demo.entities.ActivateCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ActivateCodesRepository extends JpaRepository<ActivateCodes, Integer>, JpaSpecificationExecutor<ActivateCodes> {
  ActivateCodes findByCode(Integer code);

  @Modifying
  @Query(nativeQuery = true, value = "delete from system_of_airline.activate_codes a where a.code=?1")
  void deleteByCode(Integer code);

  @Modifying
  @Transactional
  @Query(nativeQuery = true,value = "delete from system_of_airline.activate_codes ac where ac.valid<now()")
  void deleteOldCodes();

  @Modifying
  @Transactional
  @Query(nativeQuery = true,value = "delete from system_of_airline.activate_codes ac where ac.auth_user_id=?1")
  void deleteOldCodes(UUID userId);
}