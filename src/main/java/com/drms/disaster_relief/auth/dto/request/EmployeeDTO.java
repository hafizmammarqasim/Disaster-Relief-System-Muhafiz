package com.drms.disaster_relief.auth.dto.request;

import com.drms.disaster_relief.auth.enums.EmployeeJob;
import com.drms.disaster_relief.auth.enums.EmployeeWorkingStatus;
import com.drms.disaster_relief.auth.enums.RoleType;
import lombok.Data;

@Data
public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String cnic;
    private String branch;
    private RoleType role; // e.g., "CITY_ADMIN", "RESCUE_CREW"
    private EmployeeJob specialization;
    private EmployeeWorkingStatus availabilityStatus;
    private String password;
}

