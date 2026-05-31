package com.drms.disaster_relief.dto.Response;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ConsumableLogisticsDto {
    private UUID logisticsId;
    private String productName;
    private int quantityPerUnit;
    private String quantityOfUnits;
    private LocalDate expirationDate;

    // Friendly names for the frontend
    private String branchName;
    private String addedByName;
}
