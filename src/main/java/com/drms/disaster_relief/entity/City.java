package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cityId;

    @ManyToOne
    @JoinColumn(name = "provinceId")
    private Province province;

    @Column(nullable = false, unique = true)
    private String cityName;

    private LocalDateTime createdAt = LocalDateTime.now();
}