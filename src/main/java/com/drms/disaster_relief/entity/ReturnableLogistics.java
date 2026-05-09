package com.drms.disaster_relief.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@Entity
//@Table(name = "returnable_logistics")
@EqualsAndHashCode(callSuper = true)
public class ReturnableLogistics extends Logistics {
    // REMOVED @Id and @GeneratedValue from here
    private String uniqueIdentifier;
    private String condition;
    private String model;
    private int year;
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