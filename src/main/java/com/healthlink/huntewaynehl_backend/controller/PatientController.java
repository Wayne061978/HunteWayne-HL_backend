package com.healthlink.huntewaynehl_backend.controller;

import com.healthlink.huntewaynehl_backend.model.Appointment;
import com.healthlink.huntewaynehl_backend.model.Patient;
import com.healthlink.huntewaynehl_backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public Patient registerPatient(@RequestBody Patient patient) {
        return patientService.registerPatient(patient);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

    @GetMapping("/{id}/appointments")
    public List<Appointment> getAppointments(@PathVariable Long id) {
        return patientService.getAppointmentsOfPatient(id);
    }
}