package com.example.demo;

import com.example.demo.entities.*;
import com.example.demo.repositories.AirportRepository;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication {
    public final AuthUserRepository authUserRepository;
	public final PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
   @Bean
   public CommandLineRunner runner(){
	   return args -> {
		   try {
			   AuthUser admin = AuthUser.builder()
					   .email("admin123@mail.com")
					   .blocked(false)
					   .firstName("root")
					   .lastName("admin")
					   .password(passwordEncoder.encode("11223344"))
					   .role(Auditable.Role.ADMIN)
					   .build();
			   authUserRepository.save(admin);
		   }catch (Exception ignore){}
	   };
   }
}
