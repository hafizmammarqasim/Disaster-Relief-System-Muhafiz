package com.drms.disaster_relief.auth.dto.request;
import com.drms.disaster_relief.auth.enums.EmployeeJob;
import com.drms.disaster_relief.auth.enums.EmployeeWorkingStatus;
import com.drms.disaster_relief.auth.enums.RoleType;
import lombok.Data;

@Data
public class UpdateEmployeeRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private RoleType role;
    private EmployeeJob specialization;
    private EmployeeWorkingStatus employeeStatus;
}
