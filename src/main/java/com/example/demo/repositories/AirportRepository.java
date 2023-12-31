package com.example.demo.repositories;

import com.example.demo.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AirportRepository extends JpaRepository<Airport, UUID>, JpaSpecificationExecutor<Airport> {
    Airport findByName(String name);
    boolean existsAirportByName(String name);
    @Modifying
    void deleteByName(String name);
}