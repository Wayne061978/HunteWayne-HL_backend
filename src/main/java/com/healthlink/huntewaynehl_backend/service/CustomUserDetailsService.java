package com.healthlink.huntewaynehl_backend.service;

import com.healthlink.huntewaynehl_backend.model.Admin;
import com.healthlink.huntewaynehl_backend.model.Nurse;
import com.healthlink.huntewaynehl_backend.model.Patient;
import com.healthlink.huntewaynehl_backend.model.Provider;
import com.healthlink.huntewaynehl_backend.repository.AdminRepository;
import com.healthlink.huntewaynehl_backend.repository.NurseRepository;
import com.healthlink.huntewaynehl_backend.repository.PatientRepository;
import com.healthlink.huntewaynehl_backend.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private PatientRepository patientRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("🔍 Attempting to authenticate user: " + email);

        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            logger.info("✅ Admin found: " + admin.getEmail());
            logger.info("🔑 Stored Hashed Password: " + admin.getPassword());

            return new User(admin.getEmail(), admin.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(admin.getRole())));
        }

        Optional<Provider> providerOpt = providerRepository.findByEmailIgnoreCase(email);
        if (providerOpt.isPresent()) {
            Provider provider = providerOpt.get();
            String role = provider.getRole().startsWith("ROLE_") ? provider.getRole() : "ROLE_" + provider.getRole();
            logger.info("✅ Provider found: " + provider.getEmail() + " with role " + role);
            return new User(provider.getEmail(), provider.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(role)));
        }

        Optional<Nurse> nurseOpt = nurseRepository.findByEmailIgnoreCase(email);
        if (nurseOpt.isPresent()) {
            Nurse nurse = nurseOpt.get();
            String role = nurse.getRole().startsWith("ROLE_") ? nurse.getRole() : "ROLE_" + nurse.getRole();
            logger.info("✅ Nurse found: " + nurse.getEmail() + " with role " + role);
            return new User(nurse.getEmail(), nurse.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(role)));
        }

        Optional<Patient> patientOpt = patientRepository.findByEmailIgnoreCase(email);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            String role = patient.getRole().startsWith("ROLE_") ? patient.getRole() : "ROLE_" + patient.getRole();
            logger.info("✅ Patient found: " + patient.getEmail() + " with role " + role);
            return new User(patient.getEmail(), patient.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(role)));
        }

        logger.warning("❌ User not found: " + email);
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

}
