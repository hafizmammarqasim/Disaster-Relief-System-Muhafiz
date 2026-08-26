package com.drms.disaster_relief.logistics.dto;

import lombok.Data;

import java.util.List;

@Data
public class AllLogisticsResponseDto {

    private List<ReturnableLogisticsDto> returnableLogisticsList;

    private List<ConsumableLogisticsDto> consumableLogisticsList;

    private int returnableLogisticsSize;
    private int consumableLogisticsSize;
}
