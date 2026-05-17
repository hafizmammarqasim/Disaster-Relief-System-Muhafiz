package com.drms.disaster_relief.entity;

import com.drms.disaster_relief.enums.MissionStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Mission {
    @Id
    @GeneratedValue
    private UUID missionId;

    @OneToOne
    @JoinColumn(name="requestId")
    private HelpRequest request;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;

    @ManyToOne
    @JoinColumn(name="branchId")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee createdBy;

    private String missionName;

    private String type;

    private String area;

    private float locationLat;

    private float locationLng;

    private String helpType;

    private String urgencyLevel;

    private String guidelines;

    @Enumerated(EnumType.STRING)
    private MissionStatus status;

    private LocalDate expectedCompletionDate;

    private LocalDate actualCompletionDate;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "mission",
            fetch = FetchType.LAZY
    )
    private List<MissionCrewAssignment> crewAssignments;
}