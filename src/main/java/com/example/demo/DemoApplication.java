package com.example.demo;

import com.example.demo.entities.*;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.repositories.CityRepository;
import com.example.demo.util.BaseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication {
    public final AuthUserRepository authUserRepository;
	public final PasswordEncoder passwordEncoder;
	private final CityRepository cityRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
   @Bean
   public CommandLineRunner runner(){
	   return args -> {
		   try {
			   Set<String> cityNames = BaseUtil.cities;
			   cityNames.forEach(s -> {
				   City city = City.builder()
						   .name(s).build();
				   cityRepository.save(city);
				   System.out.println("city = " + city);
			   });
			   AuthUser admin = AuthUser.builder()
					   .email("admin123@mail.com")
					   .blocked(false)
					   .firstName("root")
					   .lastName("admin")
					   .password(passwordEncoder.encode("11223344"))
					   .role(Auditable.Role.ADMIN)
					   .build();
			   authUserRepository.save(admin);
			   System.out.println("admin = " + admin);
		   }catch (Exception e){
			   e.printStackTrace();
		   }
	   };
   }
}
