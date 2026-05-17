package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.Employee;
import com.drms.disaster_relief.enums.EmployeeSpecialization;
import com.drms.disaster_relief.enums.EmployeeWorkingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByEmail(String email);

    Page<Employee> findBySpecializationAndEmployeeStatus(EmployeeSpecialization specialization,
                                                         EmployeeWorkingStatus availabilityStatus, Pageable pageable);
}
