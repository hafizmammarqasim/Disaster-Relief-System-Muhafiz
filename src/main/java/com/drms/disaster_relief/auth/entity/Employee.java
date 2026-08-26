package com.drms.disaster_relief.auth.entity;

import com.drms.disaster_relief.location.entity.Branch;
import com.drms.disaster_relief.auth.enums.EmployeeJob;
import com.drms.disaster_relief.auth.enums.EmployeeWorkingStatus;
import com.drms.disaster_relief.auth.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue
    private UUID employeeId;

    @ManyToOne
    @JoinColumn(name="branchId")
    private Branch branch;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String cnic;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Enumerated(EnumType.STRING)
    private EmployeeJob specialization;

    @Enumerated(EnumType.STRING)
    private EmployeeWorkingStatus employeeStatus;

    private boolean isActive = true;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(updatable = true)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}