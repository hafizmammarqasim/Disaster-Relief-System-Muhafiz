package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class HelpRequest {
    @Id
    @GeneratedValue
    private UUID requestId;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City cityId;

    private String area;

    private String nearestLandmark;

    private float locationLat;

    private float locationLng;

    private String helpType;

    private String urgencyLevel;

    private String description;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "logId")
    private List<HelpRequestLog> requestLog;
}