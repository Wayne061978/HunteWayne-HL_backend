package com.healthlink.huntewaynehl_backend.service;

import com.healthlink.huntewaynehl_backend.model.Admin;
import com.healthlink.huntewaynehl_backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createAdminIfNotExists() {
        Optional<Admin> existingAdmin = adminRepository.findByEmail("admin@example.com");
        if (existingAdmin.isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("Admin123")); // Hash the password
            admin.setRole("ROLE_ADMIN");

            adminRepository.save(admin);
            System.out.println("✅ Default admin created: admin@example.com / Admin123");
        }
    }
}
