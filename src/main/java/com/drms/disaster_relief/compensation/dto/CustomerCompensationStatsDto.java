package com.drms.disaster_relief.compensation.dto;

import lombok.Data;

@Data
public class CustomerCompensationStatsDto {
    private long totalRequests;
    private float totalPendingAmount;
    private float totalApprovedAmount;
}