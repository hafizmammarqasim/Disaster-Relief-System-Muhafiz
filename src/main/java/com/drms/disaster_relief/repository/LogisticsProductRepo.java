package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.LogisticsProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogisticsProductRepo extends JpaRepository<LogisticsProduct, UUID> {
}
