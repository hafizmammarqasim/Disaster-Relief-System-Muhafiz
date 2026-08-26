package com.drms.disaster_relief.logistics.entity;

import com.drms.disaster_relief.logistics.enums.LogisticsType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class LogisticsProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID productId;

    //Returnable or Consumable
    @Enumerated(EnumType.STRING)
    private LogisticsType type;

    private String name;

    private final LocalDateTime createdAt = LocalDateTime.now();
}



















//package com.drms.disaster_relief.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Data
//@Entity
//public class Logistics {
//    @Id
//    @GeneratedValue
//    private UUID logisticsId;
//
//    @ManyToOne
//    @JoinColumn(name="branchId")
//    private Branch branch;
//
//    @ManyToOne
//    @JoinColumn(name="employeeId")
//    private Employee addedBy;
//
//    private String type;
//
//    private String name;
//
//    private String category;
//
//    private String status;
//
//    private LocalDateTime createdAt;
//
//    private LocalDateTime updatedAt;
//}