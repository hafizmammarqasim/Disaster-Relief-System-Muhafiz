package com.drms.disaster_relief.dto.Request;

import lombok.Data;

import java.util.UUID;

@Data
public class CrewAssignmentDto {
    private UUID missionId;

    private int driversNeeded;
    private int firefightersNeeded;
    private int medicsNeeded;
    private int sarSpecialistsNeeded; // SAR = Search and Rescue
    private int diversNeeded;
}
