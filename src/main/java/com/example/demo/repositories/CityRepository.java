package com.example.demo.repositories;

import com.example.demo.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID>, JpaSpecificationExecutor<City> {
    City findByName(String name);
    boolean existsByName(String name);
    @Modifying
    void deleteByName(String name);
}