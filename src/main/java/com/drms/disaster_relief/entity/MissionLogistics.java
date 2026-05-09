package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
//@Entity
public class MissionLogistics {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "missionId")
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "logisticsId")
    private Logistics logistics;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee confirmedBy;

    private String returnStatus;

    private String conditionOnReturn;

    private String remarks;

    private LocalDateTime assignedAt;

    private LocalDateTime returnedAt;
}