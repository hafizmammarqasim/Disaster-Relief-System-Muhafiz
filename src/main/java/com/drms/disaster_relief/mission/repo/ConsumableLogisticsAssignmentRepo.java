package com.drms.disaster_relief.mission.repo;

import com.drms.disaster_relief.mission.entity.ConsumableLogisticsAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsumableLogisticsAssignmentRepo extends JpaRepository<ConsumableLogisticsAssignment, UUID> {
}

