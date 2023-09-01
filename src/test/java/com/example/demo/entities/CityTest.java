package com.example.demo.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    @Test
    void addAirports() {
        ZoneId NewYork = ZoneId.of("America/New_York");
        ZoneId Tashkent = ZoneId.of("Asia/Tashkent");
        LocalDateTime T = LocalDateTime.parse("2023-05-16T15:30");
        ZonedDateTime zd1 = T.atZone(Tashkent);
        ZonedDateTime zd2 = zd1.plusHours(10).withZoneSameInstant(NewYork);
        LocalDateTime N = zd2.toLocalDateTime();
        System.out.println("T = " + T);
        System.out.println("N = " + N);
    }
}