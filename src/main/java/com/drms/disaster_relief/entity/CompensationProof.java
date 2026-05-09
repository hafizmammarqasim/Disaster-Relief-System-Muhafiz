package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.drms.disaster_relief.entity.CompensationRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
//@Entity
public class CompensationProof {

    @Id
    @GeneratedValue
    private UUID proofId;

    @OneToOne
    @JoinColumn(name="requestId")
    private CompensationRequest request;

    @OneToOne
    @JoinColumn(name="employeeId")
    private NGO uploadedBy;

    private String filePath;

    private String description;

    private LocalDateTime uploadedAt;
}