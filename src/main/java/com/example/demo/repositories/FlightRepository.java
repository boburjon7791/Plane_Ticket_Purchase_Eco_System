package com.example.demo.repositories;

import com.example.demo.entities.AuthUser;
import com.example.demo.entities.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID>, JpaSpecificationExecutor<Flight> {
    @Query(value = "from Flight f where f.authUsers=?1")
    Page<Flight> findAllByAuthUserId(AuthUser authUser, Pageable pageable);

    @Query(nativeQuery = true,value = "select * from system_of_airline.flight f where date(f.from_time)=?1")
    List<Flight> findAllByDate(LocalDate time);
}