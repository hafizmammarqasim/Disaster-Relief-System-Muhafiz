package com.drms.disaster_relief.mission.dto.response;

import lombok.Data;
import java.util.UUID;

@Data
public class AssignedLogisticsSummaryDto {
    private UUID missionId;
    private String missionName;
    private int returnablesAssigned;
    private int consumablesAssigned;
    private String message;
}