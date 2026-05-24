package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private UUID employeeId;

    @ManyToOne
    @JoinColumn(name="branchId")
    private Branch branch;

    private String firstName;

    private String lastName;

    private String cnic;

    private String phoneNumber;

    private String email;

    private String role;

    private String specialization;

    private String availabilityStatus;

    private boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}