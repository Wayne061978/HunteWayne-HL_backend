package com.healthlink.huntewaynehl_backend.controller;

import com.healthlink.huntewaynehl_backend.model.Patient;
import com.healthlink.huntewaynehl_backend.model.Nurse;
import com.healthlink.huntewaynehl_backend.model.Provider;
import com.healthlink.huntewaynehl_backend.repository.PatientRepository;
import com.healthlink.huntewaynehl_backend.repository.NurseRepository;
import com.healthlink.huntewaynehl_backend.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin_dashboard")
public class AdminController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private ProviderRepository providerRepository;

    // ✅ Display Admin Dashboard
    @GetMapping
    public String showAdminDashboard(Model model, Authentication authentication) {
        model.addAttribute("adminEmail", authentication.getName());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("nurses", nurseRepository.findAll());
        model.addAttribute("providers", providerRepository.findAll());
        return "admin-dashboard";
    }

    // ✅ Delete Patient
    @PostMapping("/delete-patient/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
        return "redirect:/admin_dashboard?success=Patient deleted";
    }

    // ✅ Delete Nurse
    @PostMapping("/delete-nurse/{id}")
    public String deleteNurse(@PathVariable Long id) {
        nurseRepository.deleteById(id);
        return "redirect:/admin_dashboard?success=Nurse deleted";
    }

    // ✅ Delete Provider
    @PostMapping("/delete-provider/{id}")
    public String deleteProvider(@PathVariable Long id) {
        providerRepository.deleteById(id);
        return "redirect:/admin_dashboard?success=Provider deleted";
    }


    @GetMapping("/edit-patient/{id}")
    public String editPatient(@PathVariable Long id, Model model) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            model.addAttribute("user", patient.get());
            model.addAttribute("userType", "PATIENT");
            return "edit-user"; // ✅ Ensure this matches your Thymeleaf template name
        }
        return "redirect:/admin_dashboard?error=PatientNotFound";
    }


    @GetMapping("/edit-nurse/{id}")
    public String editNurse(@PathVariable Long id, Model model) {
        Optional<Nurse> nurse = nurseRepository.findById(id);
        if (nurse.isPresent()) {
            model.addAttribute("user", nurse.get());
            model.addAttribute("userType", "NURSE");
            return "edit-user";
        }
        return "redirect:/admin_dashboard?error=NurseNotFound";
    }

    // Load Edit Page for Providers
    @GetMapping("/edit-provider/{id}")
    public String editProvider(@PathVariable Long id, Model model) {
        Optional<Provider> provider = providerRepository.findById(id);
        if (provider.isPresent()) {
            model.addAttribute("user", provider.get());
            model.addAttribute("userType", "PROVIDER");
            return "edit-user";
        }
        return "redirect:/admin_dashboard?error=ProviderNotFound";
    }

    // ✅ Handle User Update
    @PostMapping("/update-user")
    public String updateUser(@RequestParam Long id, @RequestParam String userType,
                             @RequestParam String name, @RequestParam String email,
                             @RequestParam String phone,
                             @RequestParam(required = false) String department,
                             @RequestParam(required = false) String license) {
        switch (userType) {
            case "PATIENT":
                Optional<Patient> patientOpt = patientRepository.findById(id);
                if (patientOpt.isPresent()) {
                    Patient patient = patientOpt.get();
                    patient.setFirstName(name);
                    patient.setEmail(email);
                    patient.setPhone(phone);
                    patientRepository.save(patient);
                }
                break;
            case "NURSE":
                Optional<Nurse> nurseOpt = nurseRepository.findById(id);
                if (nurseOpt.isPresent()) {
                    Nurse nurse = nurseOpt.get();
                    nurse.setName(name);
                    nurse.setEmail(email);
                    nurse.setPhone(phone);
                    nurse.setDepartment(department);
                    nurseRepository.save(nurse);
                }
                break;
            case "PROVIDER":
                Optional<Provider> providerOpt = providerRepository.findById(id);
                if (providerOpt.isPresent()) {
                    Provider provider = providerOpt.get();
                    provider.setName(name);
                    provider.setEmail(email);
                    provider.setPhone(phone);
                    providerRepository.save(provider);
                }
                break;
        }
        return "redirect:/admin_dashboard?success=UserUpdated";
    }

    public String editPatient(long l, Model model) {
        return "";// remember to remove//
    }
}
