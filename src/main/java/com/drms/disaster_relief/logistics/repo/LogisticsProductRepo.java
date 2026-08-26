package com.drms.disaster_relief.logistics.repo;

import com.drms.disaster_relief.logistics.enums.LogisticsType;
import com.drms.disaster_relief.logistics.entity.LogisticsProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LogisticsProductRepo extends JpaRepository<LogisticsProduct, UUID> {
    // Fetch only Returnable products for the loop
    List<LogisticsProduct> findByType(LogisticsType type);
}
