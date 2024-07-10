package com.dinitProject.healthcheckMonitoringApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthcheckMonitoringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthcheckMonitoringAppApplication.class, args);
	}

/*
	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
			Role userRole = roleRepository.findByName("USER").orElse(null);

			userRepository.save(new User("admin", "admin",
					"admin", passwordEncoder.encode("admin"), List.of(adminRole)));
			userRepository.save(new User("user", "user",
					"user", passwordEncoder.encode("user"), List.of(userRole)));
		};
	}*/

}
