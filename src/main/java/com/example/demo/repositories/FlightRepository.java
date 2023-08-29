package com.example.demo.repositories;

import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID>, JpaSpecificationExecutor<Flight> {
    @Query(nativeQuery = true,value = "select * from system_of_airline.flight_where auth_user_id = ?1")
    Flight findByAuthUserId(Long authUserId);
}