package com.drms.disaster_relief.entity;

import com.drms.disaster_relief.enums.LogisticsReturnStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
//This is for assignment of Returnable Logistics to a particular mission
public class ReturnableLogisticsAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID assignmentId;

    @ManyToOne
    @JoinColumn(name = "logisticsId")
    private ReturnableLogistics logistics;

    @ManyToOne
    @JoinColumn(name = "missionId")
    private Mission mission;

    @Enumerated(EnumType.STRING)
    private LogisticsReturnStatus returnStatus;

    private final LocalDate assignmentDate = LocalDate.now();
}
