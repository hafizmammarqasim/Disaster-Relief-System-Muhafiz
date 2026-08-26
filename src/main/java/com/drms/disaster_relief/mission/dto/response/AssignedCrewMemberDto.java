package com.drms.disaster_relief.mission.dto.response;

import lombok.Data;
import java.util.UUID;
import com.drms.disaster_relief.auth.enums.EmployeeJob; // Assuming your enum is here

@Data
public class AssignedCrewMemberDto {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private EmployeeJob specialization;
    private String phoneNumber;
}