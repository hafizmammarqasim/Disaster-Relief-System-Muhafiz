package com.drms.disaster_relief.dto.Request;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class AssignLogisticsDto {
    private UUID missionId;
    private List<ConsumableRequest> consumables;
    private List<ReturnableRequest> returnables;

    @Data
    public static class ConsumableRequest {
        private UUID logisticsId; // We know the exact row
        private int requestedUnits; // How many boxes/units they want
    }

    @Data
    public static class ReturnableRequest {
        private UUID productId; // We just know they want "Ambulances"
        private int quantityNeeded; // How many they want
    }
}