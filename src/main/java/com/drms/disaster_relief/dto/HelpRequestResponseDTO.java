package com.drms.disaster_relief.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class HelpRequestResponseDTO {
    private UUID requestId;
    private String helpType;      // e.g., MEDICAL
    private String urgencyLevel;  // e.g., CRITICAL
    private String status;        // e.g., IN_PROGRESS
    private String area;          // e.g., Block G
    private String description;
    private LocalDateTime createdAt;

    // The "History" part (This is what makes it professional)
    private List<HelpRequestLogDTO> history;
}
