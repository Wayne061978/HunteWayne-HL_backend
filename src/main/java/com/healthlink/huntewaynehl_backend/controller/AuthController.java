

package com.healthlink.huntewaynehl_backend.controller;

import com.healthlink.huntewaynehl_backend.model.Patient;
import com.healthlink.huntewaynehl_backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint for registering a new Patient
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody Patient patient) {
        try {
            // Check for duplicate email
            Optional<Patient> existingPatient = patientService.findByEmail(patient.getEmail());
            if (existingPatient.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A patient with this email already exists.");
            }

            // Encrypt the password
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));

            // Assign default role
            patient.setRole("ROLE_PATIENT");

            // Save the patient
            Patient savedPatient = patientService.registerPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during patient registration: " + e.getMessage());
        }
    }

    /**
     * Endpoint for user login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            if (authentication.isAuthenticated()) {
                Optional<Patient> patientOpt = patientService.findByEmail(email);
                if (patientOpt.isPresent()) {
                    Patient patient = patientOpt.get();
                    return ResponseEntity.ok("Login successful! Redirecting to dashboard...");
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed.");
    }
}
