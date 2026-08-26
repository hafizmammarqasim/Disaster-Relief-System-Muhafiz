package com.drms.disaster_relief.compensation.entity;

import com.drms.disaster_relief.auth.entity.User;
import com.drms.disaster_relief.compensation.enums.CompensationRequestStatus;
import com.drms.disaster_relief.location.entity.City;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class CompensationRequest {
    @Id
    @GeneratedValue
    private UUID requestId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne (mappedBy = "request")
    private CompensationProof proof;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;

    private String damageType;

    private String description;

    private float estimatedValue;

    private String bankName;

    private String accountTitle;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private CompensationRequestStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}