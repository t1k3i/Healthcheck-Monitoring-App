package com.dinit.healthcheck.repositorys;

import com.dinit.healthcheck.models.AlertMail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertMailRepository extends JpaRepository<AlertMail, Long> {

    Optional<AlertMail> findByMail(String email);

}
