package com.example.demo.repositories;

import com.example.demo.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AirportRepository extends JpaRepository<Airport, UUID>, JpaSpecificationExecutor<Airport> {
}