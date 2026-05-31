package com.drms.disaster_relief.dto.Response;

import com.drms.disaster_relief.entity.ConsumableLogistics;
import com.drms.disaster_relief.entity.ReturnableLogistics;
import lombok.Data;
import java.util.List;

@Data
public class LogisticsDashboardDto {
    // 1. Stats
    private long totalConsumableItems;
    private long totalReturnableAssets;
    private long availableReturnableAssets;

    // 2. Preview Lists (5 items each)
    private List<ConsumableLogistics> recentConsumables;
    private List<ReturnableLogistics> recentReturnables;
}
