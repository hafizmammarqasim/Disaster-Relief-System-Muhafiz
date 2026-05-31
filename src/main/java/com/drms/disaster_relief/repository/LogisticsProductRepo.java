package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.LogisticsProduct;
import com.drms.disaster_relief.enums.LogisticsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LogisticsProductRepo extends JpaRepository<LogisticsProduct, UUID> {
    // Fetch only Returnable products for the loop
    List<LogisticsProduct> findByType(LogisticsType type);
}
