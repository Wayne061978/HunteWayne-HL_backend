package com.healthlink.huntewaynehl_backend.controller;


import com.healthlink.huntewaynehl_backend.model.Nurse;
import com.healthlink.huntewaynehl_backend.model.Patient;
import com.healthlink.huntewaynehl_backend.model.Provider;
import com.healthlink.huntewaynehl_backend.repository.NurseRepository;
import com.healthlink.huntewaynehl_backend.repository.PatientRepository;
import com.healthlink.huntewaynehl_backend.repository.ProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Controller responsible for handling user signups for Providers, Nurses, and Patients.
 * Exposes endpoints for user signup and persistence in respective repositories.
 */
@Controller
@RequestMapping("/signup")
public class SignupPageController {

    private static final Logger logger = LoggerFactory.getLogger(SignupPageController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private NurseRepository nurseRepository;

   @Autowired
    private PatientRepository patientRepository;

    // Display the main signup page
    @GetMapping
    public String showSignupPage() {
        return "signup";
    }

    // Handle Provider Signup
    @PostMapping("/providers")
    public String handleProviderSignup(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("license") String license,
            @RequestParam("specialization") String specialization,
            Model model) {

        Optional<Provider> existingProvider = providerRepository.findByEmailIgnoreCase(email);
        if (existingProvider.isPresent()) {
            model.addAttribute("error", "Email already exists. Please use a different email.");
            return "signup";
        }

        String encodedPassword = passwordEncoder.encode(password);

        Provider provider = new Provider();
        provider.setName(name);
        provider.setEmail(email.toLowerCase());
        provider.setPassword(encodedPassword);
        provider.setLicenseNumber(license);
        provider.setSpecialization(specialization);
        provider.setRole("PROVIDER"); // ✅ Assign Role Here

        try {
            providerRepository.save(provider);
            logger.info("✅ Provider registered with role: PROVIDER");
            return "redirect:/login";
        } catch (Exception e) {
            logger.error("❌ Error saving provider: ", e);
            model.addAttribute("error", "Signup failed! Please try again.");
            return "signup";
        }
    }


    // Handle Nurse Signup
    @PostMapping("/nurses")
    public String handleNurseSignup(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("EmployeeID") String EmployeeID,
            @RequestParam("department") String department,
            Model model) {

        Optional<Nurse> existingNurse = nurseRepository.findByEmailIgnoreCase(email);
        if (existingNurse.isPresent()) {
            model.addAttribute("error", "Email already exists. Please use a different email.");
            return "signup";
        }

        String encodedPassword = passwordEncoder.encode(password);

        Nurse nurse = new Nurse();
        nurse.setName(name);
        nurse.setEmail(email.toLowerCase());
        nurse.setPassword(encodedPassword);
        nurse.setEmployeeId(EmployeeID);
        nurse.setDepartment(department);
        nurse.setRole("ROLE_NURSE"); // ✅ Assign Role Here

        try {
            nurseRepository.save(nurse);
            logger.info("✅ Nurse registered with role: ROLE_NURSE");
            return "redirect:/login";
        } catch (Exception e) {
            logger.error("❌ Error saving nurse: ", e);
            model.addAttribute("error", "Signup failed! Please try again.");
            return "signup";
        }
    }


    // Handle Patient Signup
    @PostMapping("/patients")
    public String handlePatientSignup(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("dob") String dob,
            @RequestParam("gender") String gender,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam(value = "insurance", required = false) String insurance,
            @RequestParam(value = "ssn", required = false) String ssn,
            Model model) {

        Optional<Patient> existingPatient = patientRepository.findByEmailIgnoreCase(email);
        if (existingPatient.isPresent()) {
            model.addAttribute("error", "Email already exists. Please use a different email.");
            return "signup"; // Redirect back to signup page with error message
        }

        String encodedPassword = passwordEncoder.encode(password);

        // Create a new Patient entity and save it
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setEmail(email.toLowerCase());
        patient.setPassword(encodedPassword);
        patient.setRole("PATIENT");  // ✅ Assign Role Here
        patient.setGender(gender);
        patient.setPhone(phone);
        patient.setAddress(address);
        patient.setInsurance(insurance);
        patient.setSsn(ssn);

        try {
            patientRepository.save(patient);
            logger.info("✅ Patient registered with role: PATIENT");
            return "redirect:/login";
        } catch (Exception e) {
            logger.error("❌ Error saving patient: ", e);
            model.addAttribute("error", "Signup failed! Please try again.");
            return "signup";
        }
    }

}