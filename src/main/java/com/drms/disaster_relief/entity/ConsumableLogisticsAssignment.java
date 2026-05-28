package com.drms.disaster_relief.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
//This is for assignment of Consumable Logistics to a particular mission
public class ConsumableLogisticsAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID assignmentId;

    @ManyToOne
    @JoinColumn(name = "logisticsId")
    private ConsumableLogistics logistics;

    @ManyToOne
    @JoinColumn(name = "missionId")
    private Mission mission;

    private int quantityAssigned;

    private final LocalDate assignmentDate = LocalDate.now();
}
