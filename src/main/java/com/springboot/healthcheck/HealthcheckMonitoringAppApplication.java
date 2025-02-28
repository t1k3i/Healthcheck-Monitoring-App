package com.springboot.healthcheck;

import com.springboot.healthcheck.models.Role;
import com.springboot.healthcheck.models.User;
import com.springboot.healthcheck.repositories.RoleRepository;
import com.springboot.healthcheck.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class HealthcheckMonitoringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthcheckMonitoringAppApplication.class, args);
	}

	@Bean
	CommandLineRunner initRoles(RoleRepository roleRepository, UserRepository userRepository) {
		return args -> {
			List<String> roles = List.of("USER", "ADMIN");

			for (String roleName : roles) {
				roleRepository.findByName(roleName)
						.orElseGet(() -> {
							Role role = new Role(roleName);
							return roleRepository.save(role);
						});
			}

			Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();
			Role userRole = roleRepository.findByName("USER").orElseThrow();

			String adminUsername = "admin";
			User adminUser = new User(
					"Admin",
					"User",
					adminUsername,
					new BCryptPasswordEncoder().encode("admin"),
					adminRole
			);
			userRepository.save(adminUser);

			String testUsername = "test";
			User testUser = new User(
					"Test",
					"User",
					testUsername,
					new BCryptPasswordEncoder().encode("test"),
					userRole
			);
			userRepository.save(testUser);
		};
	}
}
