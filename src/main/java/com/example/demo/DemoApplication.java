package com.example.demo;

import com.example.demo.entities.Auditable;
import com.example.demo.entities.AuthUser;
import com.example.demo.entities.City;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.ActivateCodesRepository;
import com.example.demo.repositories.AuthUserRepository;
import com.example.demo.repositories.CityRepository;
import com.example.demo.util.BaseUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RequiredArgsConstructor
@EnableAsync
@EnableCaching
@Slf4j
@EnableScheduling
public class DemoApplication {
	private final ActivateCodesRepository activateCodesRepository;
	public final AuthUserRepository authUserRepository;
	public final PasswordEncoder passwordEncoder;
	private final CityRepository cityRepository;
    private final CacheManager cacheManager;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
   @Bean
   public CommandLineRunner runner(){
	   return args -> {
		   try {
			   City byName = cityRepository.findByName("Tashkent");
			   if (byName != null) {
				   System.out.println("byName = " + byName);
			   }else{
				   Set<String> zoneIds = ZoneId.getAvailableZoneIds();
				   Collection<String> cityNames = BaseUtil.cities;
				   Collection<City> cities = new HashSet<>();
				   cityNames.forEach(s -> {
					   Optional<String> first = zoneIds.stream().filter(s1 -> s1.toUpperCase().contains(s.toUpperCase()))
							   .findFirst();
					   try {
						   String gmt = ZoneId.of(first
								   .orElseThrow(NotFoundException::new)).getRules().toString();
						   City city = City.builder()
								   .gmt(gmt)
								   .name(s).build();
						   cities.add(city);
						   System.out.println(city);
						   cityRepository.saveAll(cities);
					   } catch (Exception ignore) {
					   }
				   });
			   }
		   }catch (Exception ignore){}
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
					   System.out.println("admin = " + admin);
				   }catch (Exception ignore){}
			   };
   }
        @Bean
	public SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP)
				.bearerFormat("JWT")
				.scheme("bearer");
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().addSecurityItem(new SecurityRequirement()
						.addList("Bearer Authentication"))
				.components(new Components().addSecuritySchemes(
						"Bearer Authentication", createAPIKeyScheme()))
				.info(new Info().title("My REST API")
						.description("Some custom description of API.")
						.version("1.0").contact(new Contact().name("Soliyev Boburjon")
								.email("http://localhost:8080").url("soliyevboburjon95@@gmail.com"))
						.license(new License().name("License of API")
								.url("API license URL")));
	}


	@Bean
	public GroupedOpenApi admin() {
		return GroupedOpenApi.builder()
				.group("admin")
				.pathsToMatch("/api.city/**",
						"/api.company/**",
						"/api.airport/**",
						"/api.flight/get/**",
						"/api.flight/get-id/**",
						"/api.auth/**",
						"/api.admin/**",
						"/api.user/**")
				.build();
	}
	@Bean
	public GroupedOpenApi agent() {
		return GroupedOpenApi.builder()
				.group("agent")
				.pathsToMatch("/api.flight/**",
						"/api.city/get/**",
						"/api.city/get-id/**",
						"/api.company/get/**",
						"/api.company/get-id/**",
						"/api.airport/get/**",
						"/api.user/**",
						"/api.airport/get-id/**",
						"/api.auth/**")
				.build();
	}
	@Bean
	public GroupedOpenApi customer() {
		return GroupedOpenApi.builder()
				.group("customer")
				.pathsToMatch("/api.user/**",
						"/api.city/get/**",
						"/api.city/get-id/**",
						"/api.company/get/**",
						"/api.company/get-id/**",
						"/api.airport/get/**",
						"/api.airport/get-id/**",
						"/api.auth/**",
						"/api.flight/get/**",
						"/api.flight/get-id/**",
						"/api.flight/reserve/**",
						"/api.flight/cancel/**")
				.build();
	}
	@Scheduled(fixedDelay = 1,initialDelay = 1,timeUnit = TimeUnit.HOURS)
	public void clearOldData(){
		activateCodesRepository.deleteOldCodes();
		cacheManager.getCacheNames().parallelStream()
						.forEach(name -> Objects.requireNonNull(cacheManager.
								getCache(Objects.requireNonNullElse(name, ""))).clear());
		log.info("cleared all caches");
	}
}
