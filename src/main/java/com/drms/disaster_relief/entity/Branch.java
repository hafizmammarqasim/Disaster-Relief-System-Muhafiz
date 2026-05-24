package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Branch {

    @Id
    @GeneratedValue
    private UUID branchId;

    @ManyToOne
    @JoinColumn(name="cityId")
    private City city;

//    @OneToMany(mappedBy = "employeeId")
//    private List<Employee> employeeList;

    private String branchName;

    private String address;

    private String phoneNumber;

    private LocalDateTime createdAt;
}