package com.drms.disaster_relief.dto.Request;

import com.drms.disaster_relief.entity.Branch;
import com.drms.disaster_relief.entity.Employee;
import com.drms.disaster_relief.enums.LogisticsStatus;
import com.drms.disaster_relief.enums.LogisticsType;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AddLogisticsDto {

    private int quantityPerUnit;

    private String quantityOfUnits;

    private LocalDate expirationDate;

    private Branch branch;

    private Employee addedBy;

    //If the product is already present
    private UUID productId;

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
