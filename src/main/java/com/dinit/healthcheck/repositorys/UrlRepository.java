package com.dinit.healthcheck.repositorys;

import com.dinit.healthcheck.models.URLInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<URLInfo, Long> {

    Optional<URLInfo> findByUrl(String url);
    Optional<URLInfo> findByDisplayName(String displayName);

    @Query("SELECT DISTINCT u FROM URLInfo u LEFT JOIN FETCH u.alertMails")
    List<URLInfo> findAllWithAlertMails();

}
