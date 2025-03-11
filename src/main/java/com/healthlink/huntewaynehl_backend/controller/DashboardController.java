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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    //  Handle Patient Dashboard
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


            System.out.println(" Redirecting to patient-dashboard.html");
            return "patient_dashboard"; //  Make sure this matches the Thymeleaf file name
        }

        return "redirect:/login";
    }

    // Handle Nurse Dashboard
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
            return "nurse_dashboard";
        }

        return "redirect:/login";
    }

    //  Handle Provider Dashboard
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
            return "provider_dashboard"; //  Correct Thymeleaf file name
        }

        return "redirect:/login";
    }

    @GetMapping("/patient_dashboard")
    public String showPatientDashboard() {
        return "patient_dashboard"; // No need to add .html
    }
////////////////////////////////////////////////////////////////////////////////////////////////
@GetMapping("/user/update-profile")
public String showUpdateProfileForm(Model model, Authentication authentication) {
    String email = authentication.getName();

    Optional<Patient> patient = patientRepository.findByEmailIgnoreCase(email);
    if (patient.isPresent()) {
        model.addAttribute("userType", "PATIENT");
        model.addAttribute("user", patient.get());
        return "update-profile";
    }

    Optional<Nurse> nurse = nurseRepository.findByEmailIgnoreCase(email);
    if (nurse.isPresent()) {
        model.addAttribute("userType", "NURSE");
        model.addAttribute("user", nurse.get());
        return "update-profile";
    }

    Optional<Provider> provider = providerRepository.findByEmailIgnoreCase(email);
    if (provider.isPresent()) {
        model.addAttribute("userType", "PROVIDER");
        model.addAttribute("user", provider.get());
        return "update-profile";
    }

    return "redirect:/login?error=User not found";
}



    @PostMapping("/user/update-profile")
    public String updateProfile(@RequestParam("userType") String userType,
                                @ModelAttribute Patient updatedPatient,
                                @ModelAttribute Nurse updatedNurse,
                                @ModelAttribute Provider updatedProvider,
                                Authentication authentication) {
        String email = authentication.getName();

        switch (userType) {
            case "PATIENT":
                Optional<Patient> existingPatient = patientRepository.findByEmailIgnoreCase(email);
                if (existingPatient.isPresent()) {
                    Patient patient = existingPatient.get();
                    patient.setFirstName(updatedPatient.getFirstName());
                    patient.setLastName(updatedPatient.getLastName());
                    patient.setPhone(updatedPatient.getPhone());
                    patient.setAddress(updatedPatient.getAddress());
                    patient.setInsurance(updatedPatient.getInsurance());

                    patientRepository.save(patient);
                    return "redirect:/patient_dashboard/patient?success=Profile updated successfully";
                }
                break;

            case "NURSE":
                Optional<Nurse> existingNurse = nurseRepository.findByEmailIgnoreCase(email);
                if (existingNurse.isPresent()) {
                    Nurse nurse = existingNurse.get();
                    nurse.setName(updatedNurse.getName());
                    nurse.setDepartment(updatedNurse.getDepartment());

                    nurseRepository.save(nurse);
                    return "redirect:/nurse_dashboard/nurse?success=Profile updated successfully";
                }
                break;

            case "PROVIDER":
                Optional<Provider> existingProvider = providerRepository.findByEmailIgnoreCase(email);
                if (existingProvider.isPresent()) {
                    Provider provider = existingProvider.get();
                    provider.setName(updatedProvider.getName());
                    provider.setSpecialization(updatedProvider.getSpecialization());
                    provider.setPhone(updatedProvider.getPhone());

                    providerRepository.save(provider);
                    return "redirect:/provider_dashboard/provider?success=Profile updated successfully";
                }
                break;
        }

        return "redirect:/user/update-profile?error=Error updating profile";
    }


}
