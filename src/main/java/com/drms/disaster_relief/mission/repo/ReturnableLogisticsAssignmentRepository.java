package com.drms.disaster_relief.mission.repo;

import com.drms.disaster_relief.mission.entity.ReturnableLogisticsAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReturnableLogisticsAssignmentRepository extends JpaRepository<ReturnableLogisticsAssignment, UUID> {
}
