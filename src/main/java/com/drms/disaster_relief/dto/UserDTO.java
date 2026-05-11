package com.drms.disaster_relief.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String cnic;
    private String city;
    private String password; // Will be saved in Auth table
}
