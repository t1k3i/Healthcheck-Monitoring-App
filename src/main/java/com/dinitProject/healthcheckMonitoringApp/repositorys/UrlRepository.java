package com.dinitProject.healthcheckMonitoringApp.repositorys;

import com.dinitProject.healthcheckMonitoringApp.models.URLInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<URLInfo, Long> {

    Optional<URLInfo> findByUrl(String url);
    Optional<URLInfo> findByDisplayName(String displayName);

}
