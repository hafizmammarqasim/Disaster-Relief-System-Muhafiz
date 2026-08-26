package com.drms.disaster_relief.logistics.dto;

import com.drms.disaster_relief.logistics.enums.LogisticsType;
import com.drms.disaster_relief.logistics.enums.LogisticsStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LogisticsDetailDto {
    private String productName;
    private LogisticsType logisticsType;

    // Common fields
    private String branchName;
    private String addedBy;

    // Consumable specific (will be null for returnables)
    private Integer quantityPerUnit;
    private String quantityOfUnits;
    private LocalDate expirationDate;

    // Returnable specific (will be null for consumables)
    private String idNumber;
    private Integer year;
    private LogisticsStatus status;
}