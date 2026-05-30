package com.drms.disaster_relief.dto.Request;
import com.drms.disaster_relief.enums.*;
import lombok.Data;
import java.util.UUID;

@Data
public class UpdateEmployeeRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private RoleType role;
    private EmployeeSpecialization specialization;
    private EmployeeWorkingStatus employeeStatus;
}
