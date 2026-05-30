package com.drms.disaster_relief.dto;

import com.drms.disaster_relief.entity.Branch;
import com.drms.disaster_relief.enums.EmployeeSpecialization;
import com.drms.disaster_relief.enums.EmployeeWorkingStatus;
import com.drms.disaster_relief.enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String cnic;
    private String branch;
    private RoleType role; // e.g., "CITY_ADMIN", "RESCUE_CREW"
    private EmployeeSpecialization specialization;
    private EmployeeWorkingStatus availabilityStatus;
    private String password;
}

