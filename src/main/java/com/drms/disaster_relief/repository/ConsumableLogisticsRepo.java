package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.ConsumableLogistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsumableLogisticsRepo extends JpaRepository<ConsumableLogistics, UUID> {
}
