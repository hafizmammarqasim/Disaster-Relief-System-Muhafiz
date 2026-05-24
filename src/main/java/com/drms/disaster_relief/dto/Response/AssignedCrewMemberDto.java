package com.drms.disaster_relief.dto.Response;

import lombok.Data;
import java.util.UUID;
import com.drms.disaster_relief.enums.EmployeeSpecialization; // Assuming your enum is here

@Data
public class AssignedCrewMemberDto {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private EmployeeSpecialization specialization;
    private String phoneNumber;
}