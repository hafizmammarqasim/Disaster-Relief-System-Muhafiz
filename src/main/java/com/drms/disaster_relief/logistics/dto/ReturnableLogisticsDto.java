package com.drms.disaster_relief.logistics.dto;

import com.drms.disaster_relief.logistics.enums.LogisticsStatus;
import lombok.Data;
import java.util.UUID;

@Data
public class ReturnableLogisticsDto {
    private UUID logisticsId;
    private String productName;
    private String idNumber;
    private int year;
    private LogisticsStatus status;

    // Friendly names for the frontend
    private String branchName;
    private String addedByName;
}