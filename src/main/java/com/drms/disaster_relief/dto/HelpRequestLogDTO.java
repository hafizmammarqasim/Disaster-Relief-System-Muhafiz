package com.drms.disaster_relief.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class HelpRequestLogDTO {

    private UUID logId;

    private String status;

    private String remarks;

    private LocalDateTime changedAt;

    private String addedByName;
}