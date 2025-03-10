package com.healthlink.huntewaynehl_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "offices")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long office_id;

    private String name;
    private String location;

    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL)
    private List<Provider> providers;

    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL)
    private List<Nurse> nurses;


}