package com.healthlink.huntewaynehl_backend.controller;

import com.healthlink.huntewaynehl_backend.model.Nurse;
import com.healthlink.huntewaynehl_backend.model.Patient;
import com.healthlink.huntewaynehl_backend.model.Provider;
import com.healthlink.huntewaynehl_backend.repository.NurseRepository;
import com.healthlink.huntewaynehl_backend.repository.PatientRepository;
import com.healthlink.huntewaynehl_backend.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    @PostMapping
    public String handleLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              Model model) {

        Optional<Provider> provider = providerRepository.findByEmailIgnoreCase(email);
        Optional<Nurse> nurse = nurseRepository.findByEmailIgnoreCase(email);
        Optional<Patient> patient = patientRepository.findByEmailIgnoreCase(email);
        if (provider.isPresent() && passwordEncoder.matches(password, provider.get().getPassword())) {
            model.addAttribute("user", provider.get());
            return "redirect:/provider_dashboard";
        } else if (nurse.isPresent() && passwordEncoder.matches(password, nurse.get().getPassword())) {
            model.addAttribute("user", nurse.get());
            return "redirect:/nurse_dashboard";
        } else if (patient.isPresent() && passwordEncoder.matches(password, patient.get().getPassword())) {
            model.addAttribute("user", patient.get());
            return "redirect:/patient_dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}
