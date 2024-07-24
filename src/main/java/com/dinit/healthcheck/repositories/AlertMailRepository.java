package com.dinit.healthcheck.repositories;

import com.dinit.healthcheck.models.AlertMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlertMailRepository extends JpaRepository<AlertMail, Long> {

    Optional<AlertMail> findByMail(String email);

}
