package com.drms.disaster_relief.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue
    private UUID provinceId;

    private String provinceName;

    private LocalDateTime createdAt;
}