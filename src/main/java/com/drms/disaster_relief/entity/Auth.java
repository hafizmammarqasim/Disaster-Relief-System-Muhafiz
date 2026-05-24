package com.drms.disaster_relief.entity;

import com.drms.disaster_relief.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "auth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID authId;

    @Column(unique = true, nullable = false)
    private String loginIdentifier;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String entityType;

    private UUID entityId;

    private boolean isActive;

    private LocalDateTime lastLoginAt;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {    // this is for auto update
        updatedAt = LocalDateTime.now();
    }
}