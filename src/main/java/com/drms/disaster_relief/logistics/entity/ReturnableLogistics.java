package com.drms.disaster_relief.logistics.entity;

import com.drms.disaster_relief.auth.entity.Employee;
import com.drms.disaster_relief.location.entity.Branch;
import com.drms.disaster_relief.logistics.enums.LogisticsStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Entity

public class ReturnableLogistics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID logisticsId;

    @NotNull
    private String IdNumber;

    @ManyToOne
    @JoinColumn(name = "productId")
    private LogisticsProduct productInfo;

    private String modelName;

    private int year;

    @ManyToOne
    @JoinColumn(name="branchId")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name="employeeId")
    private Employee addedBy;

    @Enumerated(EnumType.STRING)
    private LogisticsStatus status;
}


















//package com.drms.disaster_relief.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//@Data
//@Entity
//@EqualsAndHashCode(callSuper = true)
//public class ReturnableLogistics extends Logistics {
//    @Id
//    @GeneratedValue
//    private String uniqueIdentifier;
//
//    private String condition;
//
//    private String model;
//
//    private int year;
//}