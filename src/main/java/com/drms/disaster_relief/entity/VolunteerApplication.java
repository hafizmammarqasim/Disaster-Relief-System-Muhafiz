package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
//@Entity
public class VolunteerApplication {
    @Id
    @GeneratedValue
    private UUID applicationId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ngoId")
    private NGO ngo;

    private String skills;

    private String availability;

    private boolean ownTransport;

    private LocalDateTime createdAt;
}