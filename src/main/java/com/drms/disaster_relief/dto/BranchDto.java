package com.drms.disaster_relief.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class BranchDto {
    private String branchName;
    private String address;
    private String phoneNumber;
    private UUID cityId;
}