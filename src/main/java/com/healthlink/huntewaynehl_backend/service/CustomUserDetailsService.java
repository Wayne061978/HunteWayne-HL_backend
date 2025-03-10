package com.healthlink.huntewaynehl_backend.service;

import com.healthlink.huntewaynehl_backend.model.Nurse;
import com.healthlink.huntewaynehl_backend.model.Patient;
import com.healthlink.huntewaynehl_backend.model.Provider;
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
    private ProviderRepository providerRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("🔍 Attempting to authenticate user: " + email);

        Optional<Provider> providerOpt = providerRepository.findByEmailIgnoreCase(email);
        if (providerOpt.isPresent()) {
            Provider provider = providerOpt.get();
            logger.info("✅ Provider found: " + provider.getEmail() + " with role " + provider.getRole());
            return new User(provider.getEmail(), provider.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(provider.getRole())));
        }

        Optional<Nurse> nurseOpt = nurseRepository.findByEmailIgnoreCase(email);
        if (nurseOpt.isPresent()) {
            Nurse nurse = nurseOpt.get();
            logger.info("✅ Nurse found: " + nurse.getEmail() + " with role " + nurse.getRole());
            return new User(nurse.getEmail(), nurse.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(nurse.getRole())));
        }

        Optional<Patient> patientOpt = patientRepository.findByEmailIgnoreCase(email);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            logger.info("✅ Patient found: " + patient.getEmail() + " with role " + patient.getRole());
            return new User(patient.getEmail(), patient.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(patient.getRole())));
        }

        logger.warning("❌ User not found: " + email);
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

}
