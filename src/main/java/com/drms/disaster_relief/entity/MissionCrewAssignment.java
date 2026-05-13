package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class MissionCrewAssignment {
    @Id
    @GeneratedValue
    private UUID assignmentId;

    @ManyToOne
    @JoinColumn(name = "missionId")
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    private boolean acknowledged;

    private LocalDateTime assignedAt = LocalDateTime.now();
}