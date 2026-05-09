package com.drms.disaster_relief.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
//@Entity
//@Table(name = "consumable_logistics")
public class ConsumableLogistics extends Logistics {
    private int quantity;
    private String unit;
    private LocalDate expirationDate;
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