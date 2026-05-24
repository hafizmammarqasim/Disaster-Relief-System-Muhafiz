package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class ReliefCamp {
    @Id
    @GeneratedValue
    private UUID campId;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;

    private String campName;

    private String fullAddress;

    private float locationLat;

    private float locationLng;

    private int totalCapacity;

    private int currentOccupancy;

    private boolean availableFood;

    private boolean availableWater;

    private boolean availableMedical;

    private boolean availableShelter;

    private String coordinatorName;

    private String coordinatorPhoneNumber;

    private String status;

    private LocalDateTime createdAt;
}