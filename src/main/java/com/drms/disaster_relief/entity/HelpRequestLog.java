package com.drms.disaster_relief.entity;

import com.drms.disaster_relief.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class HelpRequestLog {
    @Id
    @GeneratedValue
    private UUID logId;

    @ManyToOne
    @JoinColumn(name = "requestId")
    private HelpRequest request;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee addedBy;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private String remarks;

    private LocalDateTime changedAt;
}