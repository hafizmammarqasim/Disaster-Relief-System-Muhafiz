package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MissionRepository extends JpaRepository<Mission, UUID> {

    //No need to wrap inside the optional, because database doesn't return null in case of no record
    //In case of list, it returns just empty list from here...
    List<Mission> findByCreatedBy(UUID id);

    List<Mission> findByCrewAssignments_Employee_EmployeeId(UUID employeeId);
}
