package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.ConsumableLogisticsAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsumableLogisticsAssignmentRepo extends JpaRepository<ConsumableLogisticsAssignment, UUID> {
}

