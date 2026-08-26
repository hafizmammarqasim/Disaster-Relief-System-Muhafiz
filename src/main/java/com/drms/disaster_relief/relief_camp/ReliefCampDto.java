package com.drms.disaster_relief.relief_camp;

import com.drms.disaster_relief.auth.entity.Employee;
import com.drms.disaster_relief.location.entity.City;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ReliefCampDto {
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

    private Employee managedBy;

    private String status;
}
