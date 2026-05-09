package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
//@Entity
public class MissionNgoAssignment {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "missionId")
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "ngoId")
    private NGO ngo;

    private String responseStatus;

    private boolean proposedLogistics;

    private int durationDays;

    private String remarks;

    private LocalDateTime assignedAt;
}