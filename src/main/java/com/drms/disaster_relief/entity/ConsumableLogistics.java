package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
//@Table(name = "consumable_logistics")
public class ConsumableLogistics{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID logisticsId;

    private int quantityPerUnit;

    private String quantityOfUnits;

    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name="branchId")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name="employeeId")
    private Employee addedBy;

    @ManyToOne
    @JoinColumn(name = "productId")
    private LogisticsProduct productInfo;

    private String status;
}













//package com.drms.disaster_relief.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import java.time.LocalDate;
//
//@Data
//@EqualsAndHashCode(callSuper = true)
//@Entity
//public class ConsumableLogistics extends Logistics {
//
//    private int quantity;
//
//    private String unit;
//
//    private LocalDate expirationDate;
//}