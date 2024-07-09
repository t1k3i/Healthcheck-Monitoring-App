package com.dinitProject.healthcheckMonitoringApp.repositorys;

import com.dinitProject.healthcheckMonitoringApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
