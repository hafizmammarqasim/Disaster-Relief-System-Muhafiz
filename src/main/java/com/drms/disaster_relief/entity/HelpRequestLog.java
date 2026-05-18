package com.drms.disaster_relief.entity;

import com.drms.disaster_relief.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "help_request_logs")
public class HelpRequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestId", nullable = false)
    private HelpRequest request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId")
    private Employee addedBy;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private String remarks;

    private LocalDateTime changedAt = LocalDateTime.now();
}