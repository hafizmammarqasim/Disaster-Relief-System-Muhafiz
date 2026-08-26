package com.drms.disaster_relief.mission.entity;

import com.drms.disaster_relief.auth.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class MissionLog {
    @Id
    @GeneratedValue
    private UUID logId;

    @ManyToOne
    @JoinColumn(name = "missionId")
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee changedBy;

    private String changedByType;

    private String status;

    private String remarks;

    private LocalDateTime changedAt;
}