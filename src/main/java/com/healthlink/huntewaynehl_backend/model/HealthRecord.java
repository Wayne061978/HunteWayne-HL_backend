package com.healthlink.huntewaynehl_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "health_record")
public class HealthRecord {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long health_record_id;

    private String medicalHistory;
    private String allergies;
    private String medications;

    @OneToOne(mappedBy = "healthRecord")
    private Patient patient;
}
