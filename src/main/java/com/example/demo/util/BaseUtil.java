package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@PropertySource("classpath:application-cities.yaml")
public class BaseUtil {
    @Value ("${cities}")
    public static Set<String> cities = new HashSet<>();
}
