package com.dinitProject.healthcheckMonitoringApp;

import com.dinitProject.healthcheckMonitoringApp.models.Role;
import com.dinitProject.healthcheckMonitoringApp.models.User;
import com.dinitProject.healthcheckMonitoringApp.repositorys.RoleRepository;
import com.dinitProject.healthcheckMonitoringApp.repositorys.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class HealthcheckMonitoringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthcheckMonitoringAppApplication.class, args);
	}


	/*@Bean
	@Transactional
	public CommandLineRunner commandLineRunner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			userRepository.save(new User("admin", "admin",
					"admin", passwordEncoder.encode("admin"), Set.of(new Role("ADMIN"))));
			userRepository.save(new User("user", "user",
					"user", passwordEncoder.encode("user"), Set.of(new Role("USER"))));
		};
	}*/

}
