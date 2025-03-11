package com.healthlink.huntewaynehl_backend.controller;

import com.healthlink.huntewaynehl_backend.model.Patient;
import com.healthlink.huntewaynehl_backend.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testEditPatient_Success() {
//        Patient patient = new Patient();
//        patient.setId(1L);
//        patient.setFirstName("John");
//        patient.setEmail("john@example.com");
//
//        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
//
//        String view = adminController.editPatient(1L, model);
//
//        assertEquals("edit-user", view);
//        verify(model).addAttribute("user", patient);
//        verify(model).addAttribute("userType", "PATIENT");
//    }

    @Test
    void testEditPatient_NotFound() {
        when(patientRepository.findById(999L)).thenReturn(Optional.empty());

        String view = adminController.editPatient(999L, model);

        assertEquals("redirect:/admin_dashboard?error=PatientNotFound", view);
    }
}
