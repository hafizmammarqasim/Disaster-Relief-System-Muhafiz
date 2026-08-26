package com.drms.disaster_relief.mission.entity;

import com.drms.disaster_relief.auth.entity.Employee;
import com.drms.disaster_relief.ngo.NGO;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskCompletionReport {
    @Id
    @GeneratedValue
    private UUID reportId;

    @ManyToOne
    @JoinColumn(name = "missionId")
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "ngoId")
    private NGO ngo;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee approvedBy;

    private int peopleHelped;

    private String resourcesUsed;

    private String challenges;

    private String filePath;

    private boolean approved;

    private LocalDateTime submittedAt;

    private LocalDateTime approvedAt;
}