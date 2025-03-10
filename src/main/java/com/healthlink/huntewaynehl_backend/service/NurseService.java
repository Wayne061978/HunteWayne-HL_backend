package com.healthlink.huntewaynehl_backend.service;

import com.healthlink.huntewaynehl_backend.model.Nurse;
import com.healthlink.huntewaynehl_backend.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NurseService {

    @Autowired
    private NurseRepository nurseRepository;

    public Nurse addNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    public Nurse getNurseById(Long id) {
        return nurseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nurse not found with ID: " + id));
    }

    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    public void deleteNurse(Long id) {
        nurseRepository.deleteById(id);
    }
}