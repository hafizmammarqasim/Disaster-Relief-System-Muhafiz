package com.drms.disaster_relief.dto.Request;
import lombok.Data;

@Data
public class UpdateMyProfileRequestDto {
    private String email;
    private String phoneNumber;
}