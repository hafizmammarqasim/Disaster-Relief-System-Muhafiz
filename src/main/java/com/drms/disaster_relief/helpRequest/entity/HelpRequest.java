package com.drms.disaster_relief.helpRequest.entity;

import com.drms.disaster_relief.auth.entity.User;
import com.drms.disaster_relief.helpRequest.enums.HelpType;
import com.drms.disaster_relief.helpRequest.enums.RequestStatus;
import com.drms.disaster_relief.helpRequest.enums.UrgencyLevel;
import com.drms.disaster_relief.location.entity.City;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "help-requests")
public class HelpRequest {
    @Id
    @GeneratedValue
    private UUID requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City cityId;

    private String area;

    private String nearestLandmark;

    private float locationLat;

    private float locationLng;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HelpType helpType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UrgencyLevel urgencyLevel;

    private String description;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "request")
    private List<HelpRequestLog> requestLog;


    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}