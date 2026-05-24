package com.drms.disaster_relief.dto.Request;

import com.drms.disaster_relief.entity.Branch;
import lombok.Data;

@Data
public class EmployeeSignUpRequest {

    private Branch branch;

    private String firstName;

    private String lastName;

    private String cnic;

    private String phoneNumber;

    private String email;

    private String role;

    private String specialization;

    private String availabilityStatus;

    private String loginIdentifier;

    private String password;
}
