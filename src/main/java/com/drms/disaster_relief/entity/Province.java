package com.drms.disaster_relief.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID provinceId;

    @Column(nullable = false, unique = true)
    private String provinceName;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}