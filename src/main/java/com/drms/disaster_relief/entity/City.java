package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class City {
    @Id
    @GeneratedValue
    private UUID cityId;

    @ManyToOne
    @JoinColumn(name = "provinceId")
    private Province province;

    private String cityName;

    private LocalDateTime createdAt;
}