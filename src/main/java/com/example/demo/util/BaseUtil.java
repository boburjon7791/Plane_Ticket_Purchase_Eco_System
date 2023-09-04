package com.example.demo.util;
import lombok.NonNull;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Stream;

public class BaseUtil {
    public static final Collection<String> cities = new HashSet<>();
    public static final Collection<String> gmt = new HashSet<>();
    public static final Map<String, String> gmtsMap = new TreeMap<>();
    static {
        try (InputStream is = BaseUtil.class.getClassLoader().getResourceAsStream("application-cities.properties")) {
            assert is != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    cities.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (InputStream is = BaseUtil.class.getClassLoader().getResourceAsStream("rules.txt")) {
            assert is != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    gmt.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        gmt.forEach(gmt -> {
            String zoneId = zoneId(gmt);
            gmtsMap.put(gmt, zoneId);
        });
        gmtsMap.forEach((key, value) -> System.out.println(key+": "+value));
    }
    private static String zoneId(String gmt){
        return ZoneId.getAvailableZoneIds()
                .stream()
                .filter(s -> ZoneId.of(s).getRules().toString().equals(gmt))
                .findFirst().orElseThrow();
    }
}