package com.drms.disaster_relief.auth.dto.request;
import lombok.Data;

@Data
public class UpdateMyProfileRequestDto {
    private String email;
    private String phoneNumber;
}