package com.drms.disaster_relief.dto.Response;
import com.drms.disaster_relief.enums.*;
import lombok.Data;
import java.util.UUID;

@Data
public class EmployeeDetailsResponseDto {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String cnic;
    private String email;
    private String phoneNumber;
    private RoleType role;
    private EmployeeSpecialization specialization;
    private EmployeeWorkingStatus employeeStatus;
    private String branchName;
}
