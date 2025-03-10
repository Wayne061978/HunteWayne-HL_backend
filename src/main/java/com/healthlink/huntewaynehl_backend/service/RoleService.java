package com.healthlink.huntewaynehl_backend.service;

import com.healthlink.huntewaynehl_backend.model.*;
import com.healthlink.huntewaynehl_backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void assignRoleToUser(String email, String roleName) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        Optional<Role> roleOpt = roleRepository.findByName(roleName);

        if (userOpt.isPresent() && roleOpt.isPresent()) {
            User user = userOpt.get();
            Role role = roleOpt.get();
            user.getRoles().add(role);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User or Role not found");
        }
    }
}