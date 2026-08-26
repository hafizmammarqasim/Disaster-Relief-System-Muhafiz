package com.drms.disaster_relief.notification;

import com.drms.disaster_relief.auth.entity.Auth;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue
    private UUID notificationId;

    @ManyToOne
    @JoinColumn(name = "authId")
    private Auth auth;

    private String title;

    private String message;

    private boolean isRead;

    private LocalDateTime createdAt;
}