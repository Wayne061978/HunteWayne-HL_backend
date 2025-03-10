package com.healthlink.huntewaynehl_backend.service;

import com.healthlink.huntewaynehl_backend.model.HealthRecord;
import com.healthlink.huntewaynehl_backend.repository.HealthRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthRecordService {

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    public HealthRecord addOrUpdateHealthRecord(HealthRecord healthRecord) {
        return healthRecordRepository.save(healthRecord);
    }

    public HealthRecord getHealthRecordById(Long id) {
        return healthRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HealthRecord not found with ID: " + id));
    }

    public void deleteHealthRecord(Long id) {
        healthRecordRepository.deleteById(id);
    }
}