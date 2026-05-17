package com.drms.disaster_relief.dto.Response;

import lombok.Data;

import java.util.List;
import java.util.UUID;
import com.drms.disaster_relief.enums.MissionStatus; // Assuming your enum is here

@Data
public class MissionDispatchResponseDto {
    // Core Mission Info
    private UUID missionId;
    private String missionName;
    private MissionStatus status; // e.g., "CREW_ASSIGNED"
    private String cityName; // Send the name, not the whole City object
    private String branchName; // Send the name, not the whole Branch object

    // The Assigned Crew
    private List<AssignedCrewMemberDto> assignedCrew;
}