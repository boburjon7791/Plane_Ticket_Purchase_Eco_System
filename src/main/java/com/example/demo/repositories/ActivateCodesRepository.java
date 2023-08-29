package com.example.demo.repositories;

import com.example.demo.entities.ActivateCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

public interface ActivateCodesRepository extends JpaRepository<ActivateCodes, Integer>, JpaSpecificationExecutor<ActivateCodes> {
  ActivateCodes findByCode(Integer code);

  @Modifying
  void deleteByCode(Integer code);
}