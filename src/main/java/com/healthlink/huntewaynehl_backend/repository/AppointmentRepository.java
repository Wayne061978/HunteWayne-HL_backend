package com.healthlink.huntewaynehl_backend.repository;

import com.healthlink.huntewaynehl_backend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
