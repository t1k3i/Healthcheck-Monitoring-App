package com.dinit.healthcheck.repositories;

import com.dinit.healthcheck.models.AlertMail;
import com.dinit.healthcheck.models.URLInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<URLInfo, Long> {

    Optional<URLInfo> findByUrl(String url);
    Optional<URLInfo> findByDisplayName(String displayName);

    @Query("SELECT DISTINCT u FROM URLInfo u LEFT JOIN FETCH u.alertMails")
    List<URLInfo> findAllWithAlertMails();

    @Query("SELECT u FROM URLInfo u LEFT JOIN FETCH u.alertMails WHERE u.id = :urlId")
    Optional<URLInfo> findByIdWithAlertMails(@Param("urlId") Long urlId);

    @Query("SELECT am FROM AlertMail am JOIN FETCH am.urlInfos u WHERE u.id = :urlId")
    List<AlertMail> findAlertMailsByUrlId(@Param("urlId") Long urlId);

    List<URLInfo> findByUrlContainingOrDisplayNameContaining(String urlKeyword, String displayNameKeyword);

    @Query("SELECT u FROM URLInfo u " +
            "ORDER BY " +
            "CASE WHEN u.healthy IS NULL THEN 1 ELSE 0 END, " +
            "CASE WHEN u.mute = true THEN 1 ELSE 0 END, " +
            "u.healthy DESC, " +
            "u.id DESC")
    List<URLInfo> findAllOrderByHealthyDescThenById();

    @Query("SELECT u FROM URLInfo u " +
            "ORDER BY " +
            "CASE WHEN u.healthy IS NULL THEN 1 ELSE 0 END, " +
            "CASE WHEN u.mute = true THEN 1 ELSE 0 END, " +
            "u.healthy ASC, " +
            "u.id DESC")
    List<URLInfo> findAllOrderByHealthyAscThenById();

}
