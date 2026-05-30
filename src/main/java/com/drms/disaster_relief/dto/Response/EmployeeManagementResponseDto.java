package com.drms.disaster_relief.dto.Response;

import com.drms.disaster_relief.enums.EmployeeSpecialization;
import com.drms.disaster_relief.enums.EmployeeWorkingStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeManagementResponseDto {
    private UUID employeeId;
    private String fullName;
    private EmployeeSpecialization specialization;
    private EmployeeWorkingStatus currentStatus;
}