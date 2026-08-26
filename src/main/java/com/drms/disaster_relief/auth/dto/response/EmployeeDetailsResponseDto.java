package com.drms.disaster_relief.auth.dto.response;
import com.drms.disaster_relief.auth.enums.EmployeeJob;
import com.drms.disaster_relief.auth.enums.EmployeeWorkingStatus;
import com.drms.disaster_relief.auth.enums.RoleType;
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
    private EmployeeJob specialization;
    private EmployeeWorkingStatus employeeStatus;
    private String branchName;
}
