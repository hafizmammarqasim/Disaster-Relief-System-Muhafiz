package com.drms.disaster_relief.mission.repo;

import com.drms.disaster_relief.mission.entity.MissionCrewAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CrewAssignmentRepository extends JpaRepository<MissionCrewAssignment, UUID> {



}
