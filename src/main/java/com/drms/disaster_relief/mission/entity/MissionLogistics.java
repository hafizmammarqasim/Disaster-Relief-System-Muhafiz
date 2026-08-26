package com.drms.disaster_relief.mission.entity;

import com.drms.disaster_relief.auth.entity.Employee;
import com.drms.disaster_relief.logistics.entity.LogisticsProduct;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

// Right now at 29 may and 12 am  think this class is unnecessary if this logic of no inheritance
// and separate tables of returnable and consumable logistics work...
@Data
@Entity
public class MissionLogistics {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "missionId")
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "logisticsId")
    private LogisticsProduct logistics;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee confirmedBy;

    private String returnStatus;

    private String remarks;

    private LocalDateTime assignedAt = LocalDateTime.now();

    private LocalDateTime returnedAt;
}