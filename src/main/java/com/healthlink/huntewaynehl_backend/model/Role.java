package com.healthlink.huntewaynehl_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}