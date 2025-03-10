package com.healthlink.huntewaynehl_backend.service;

import com.healthlink.huntewaynehl_backend.model.Office;
import com.healthlink.huntewaynehl_backend.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepository officeRepository;

    public Office addOffice(Office office) {
        return officeRepository.save(office);
    }

    public Office getOfficeById(Long id) {
        return officeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Office not found with ID: " + id));
    }

    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    public void deleteOffice(Long id) {
        officeRepository.deleteById(id);
    }
}