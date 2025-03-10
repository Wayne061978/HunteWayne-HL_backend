package com.healthlink.huntewaynehl_backend.repository;

import com.healthlink.huntewaynehl_backend.model.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
}
