package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

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
    private String helpType;

    @Column(nullable = false)
    private String urgencyLevel;

    private String description;

    private String status;

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