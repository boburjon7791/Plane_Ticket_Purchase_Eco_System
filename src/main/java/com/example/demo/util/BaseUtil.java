package com.example.demo.util;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BaseUtil {
    public static Collection<String> cities = new HashSet<>();
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
    }

}