package com.dinitProject.healthcheckMonitoringApp.repositorys;

import com.dinitProject.healthcheckMonitoringApp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
