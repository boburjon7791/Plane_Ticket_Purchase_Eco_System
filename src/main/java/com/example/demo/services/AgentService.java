package com.example.demo.services;

import com.example.demo.entities.Flight;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface AgentService {
    void sendReportEditFlight(Flight flight, LocalDateTime oldFromTime,LocalDateTime oldToTime);
}
