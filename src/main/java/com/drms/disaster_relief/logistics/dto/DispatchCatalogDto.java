package com.drms.disaster_relief.logistics.dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class DispatchCatalogDto {
    private List<ConsumableSummary> consumables;
    private List<ReturnableSummary> returnables;

    @Data
    public static class ConsumableSummary {
        private UUID logisticsId; // The exact row ID for the branch
        private String productName;
        private int quantityPerUnit;
        private String availableUnits;
    }

    @Data
    public static class ReturnableSummary {
        private UUID productId; // Grouped by Catalog ID
        private String productName;
        private long availableCount;
    }
}