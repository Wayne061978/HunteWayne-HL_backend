package com.healthlink.huntewaynehl_backend.service;

import com.healthlink.huntewaynehl_backend.model.Appointment;
import com.healthlink.huntewaynehl_backend.model.Patient;
import com.healthlink.huntewaynehl_backend.repository.AppointmentRepository;
import com.healthlink.huntewaynehl_backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Patient registerPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + id));
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentsOfPatient(Long patientId) {
        Patient patient = getPatientById(patientId);
        return patient.getAppointments();
    }

    public Optional<Patient> findByEmail(String email) {
        return Optional.empty();
    }
}