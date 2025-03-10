package com.healthlink.huntewaynehl_backend.repository;

import com.healthlink.huntewaynehl_backend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
