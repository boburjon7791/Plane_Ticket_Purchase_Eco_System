package com.example.demo.repositories;

import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.*;

import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID>, JpaSpecificationExecutor<Flight> {
    @Query(value = "from Flight f where f.authUsers=?1")
    Page<Flight> findAllByAuthUserId(AuthUser authUser, Pageable pageable);
}