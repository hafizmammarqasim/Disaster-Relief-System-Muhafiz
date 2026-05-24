package com.drms.disaster_relief.entity;

import com.drms.disaster_relief.enums.EmployeeSpecialization;
import com.drms.disaster_relief.enums.EmployeeWorkingStatus;
import com.drms.disaster_relief.enums.RoleType;
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

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Enumerated(EnumType.STRING)
    private EmployeeSpecialization specialization;

    @Enumerated(EnumType.STRING)
    private EmployeeWorkingStatus employeeStatus;

    private boolean isActive;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

}