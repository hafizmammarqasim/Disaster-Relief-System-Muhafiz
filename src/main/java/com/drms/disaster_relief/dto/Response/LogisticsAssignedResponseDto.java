package com.drms.disaster_relief.dto.Response;

import lombok.Data;
import java.util.UUID;

@Data
public class LogisticsAssignedResponseDto {
    private UUID missionId;
    private String missionName;
    private int returnablesAssigned;
    private int consumablesAssigned;
    private String message;
}