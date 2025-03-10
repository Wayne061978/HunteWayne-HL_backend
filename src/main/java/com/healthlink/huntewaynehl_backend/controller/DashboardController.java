package com.healthlink.huntewaynehl_backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.healthlink.huntewaynehl_backend.model.Nurse;
import com.healthlink.huntewaynehl_backend.model.Patient;
import com.healthlink.huntewaynehl_backend.model.Provider;
import com.healthlink.huntewaynehl_backend.repository.NurseRepository;
import com.healthlink.huntewaynehl_backend.repository.PatientRepository;
import com.healthlink.huntewaynehl_backend.repository.ProviderRepository;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class DashboardController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private ProviderRepository providerRepository;

    // ✅ Handle Patient Dashboard
    @GetMapping("/patient_dashboard/patient")
    public String showPatientDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("User is not authenticated, redirecting to login...");
            return "redirect:/login";
        }

        String email = authentication.getName();
        System.out.println("Logged-in user email: " + email);

        Optional<Patient> patient = patientRepository.findByEmailIgnoreCase(email);
        if (patient.isPresent()) {
            model.addAttribute("userName", patient.get().getFirstName());
            model.addAttribute("appointments", new ArrayList<>()); // Example empty list
            System.out.println("✅ Redirecting to patient-dashboard.html");
            return "patient_dashboard"; // ✅ Make sure this matches the Thymeleaf file name
        }

        return "redirect:/login";
    }

    // ✅ Handle Nurse Dashboard
    @GetMapping("/nurse_dashboard/nurse")
    public String showNurseDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String email = authentication.getName();
        Optional<Nurse> nurse = nurseRepository.findByEmailIgnoreCase(email);
        if (nurse.isPresent()) {
            model.addAttribute("nurse", nurse.get());
            return "nurse_dashboard"; // ✅ Correct Thymeleaf file name
        }

        return "redirect:/login";
    }

    // ✅ Handle Provider Dashboard
    @GetMapping("/provider_dashboard/provider")
    public String showProviderDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String email = authentication.getName();
        Optional<Provider> provider = providerRepository.findByEmailIgnoreCase(email);
        if (provider.isPresent()) {
            model.addAttribute("provider", provider.get());
            return "provider_dashboard"; // ✅ Correct Thymeleaf file name
        }

        return "redirect:/login";
    }
}
