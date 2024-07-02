package com.dinitProject.healthcheckMonitoringApp.Repositorys;

import com.dinitProject.healthcheckMonitoringApp.Models.URLInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<URLInfo, Long> {
}
