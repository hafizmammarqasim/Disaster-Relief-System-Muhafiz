package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "NGOs")
public class NGO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ngoId;

    @Column(nullable = false)
    private String organizationName;

    @Column(unique = true, nullable = false)
    private String registrationNumber;

    @Column(nullable = false)
    private String contactPersonName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    private String website;

    private String description;

    private int trustScore;

    private boolean isActive;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void inUpdate() {
        updatedAt = LocalDateTime.now();
    }
}