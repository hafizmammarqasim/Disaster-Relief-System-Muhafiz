package com.drms.disaster_relief.dto.Response;

import com.drms.disaster_relief.enums.LogisticsType;
import com.drms.disaster_relief.enums.LogisticsStatus;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

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