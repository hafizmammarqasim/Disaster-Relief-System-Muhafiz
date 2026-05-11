package com.drms.disaster_relief.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;    // Or loginIdentifier
    private String password;
}
