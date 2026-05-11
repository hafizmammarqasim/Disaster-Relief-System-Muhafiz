package com.drms.disaster_relief.dto;

import com.drms.disaster_relief.entity.Branch;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String cnic;
    private Branch branchId; // Industry standard: Send the ID
    private String role; // e.g., "CITY_ADMIN", "RESCUE_CREW"
    private String specialization;
    private String availabilityStatus;
    private String password;
}

