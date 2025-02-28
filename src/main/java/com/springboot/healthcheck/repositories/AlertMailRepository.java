package com.springboot.healthcheck.repositories;

import com.springboot.healthcheck.models.AlertMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlertMailRepository extends JpaRepository<AlertMail, Long> {

    Optional<AlertMail> findByMail(String email);

}
