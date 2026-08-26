package com.drms.disaster_relief.auth.dto.response;

import com.drms.disaster_relief.auth.enums.EmployeeJob;
import com.drms.disaster_relief.auth.enums.EmployeeWorkingStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeManagementResponseDto {
    private UUID employeeId;
    private String fullName;
    private EmployeeJob specialization;
    private EmployeeWorkingStatus currentStatus;
}