package com.drms.disaster_relief.logistics.dto;

import com.drms.disaster_relief.location.entity.Branch;
import com.drms.disaster_relief.auth.entity.Employee;
import com.drms.disaster_relief.logistics.enums.LogisticsStatus;
import com.drms.disaster_relief.logistics.enums.LogisticsType;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AddLogisticsDto {


    //If the product is already present
    private UUID productId;

    private int quantityPerUnit;

    private String quantityOfUnits;

    private LocalDate expirationDate;

    private Branch branch;

    private Employee addedBy;

    //To check whether logistics is returnable or consumable
    private LogisticsType type;

    //Data of returnable logistics (Instance or unique record data)
    private String name;

    private String IdNumber;

    private String modelName;

    private int year;

    //Logistics Status (enum is used)
    private LogisticsStatus logisticsStatus;

}
