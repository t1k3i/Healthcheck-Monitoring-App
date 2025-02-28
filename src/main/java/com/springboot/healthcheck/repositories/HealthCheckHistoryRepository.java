package com.springboot.healthcheck.repositories;

import com.springboot.healthcheck.models.HealthCheckHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface HealthCheckHistoryRepository extends JpaRepository<HealthCheckHistory, Long> {

    @Query("SELECT h FROM HealthCheckHistory h WHERE h.urlInfo.id = :urlInfoId ORDER BY h.checked DESC")
    Page<HealthCheckHistory> findByUrlInfoId(@Param("urlInfoId") Long urlInfoId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM HealthCheckHistory h WHERE h.urlInfo.id = :urlInfoId")
    void deleteByUrlInfoId(@Param("urlInfoId") Long urlInfoId);

}
