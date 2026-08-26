package com.drms.disaster_relief.auth.dto.request;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;    // Or loginIdentifier
    private String password;
}
