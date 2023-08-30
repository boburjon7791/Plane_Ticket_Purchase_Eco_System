package com.example.demo.util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BaseUtil {
    public static Collection<String> cities = new HashSet<>();
    static {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/cities.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                cities.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}