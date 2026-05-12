package com.drms.disaster_relief.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class HelpRequestDTO {
    private UUID cityId;         // The user selects this from a dropdown
    private String area;
    private String nearestLandmark;
    private float locationLat;
    private float locationLng;
    private String helpType;      // e.g., "FOOD", "RESCUE"
    private String urgencyLevel;  // e.g., "CRITICAL", "MODERATE"
    private String description;
}