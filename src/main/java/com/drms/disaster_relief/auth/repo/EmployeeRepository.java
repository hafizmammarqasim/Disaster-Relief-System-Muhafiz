package com.drms.disaster_relief.auth.repo;

import com.drms.disaster_relief.auth.entity.Employee;
import com.drms.disaster_relief.auth.enums.EmployeeJob;
import com.drms.disaster_relief.auth.enums.EmployeeWorkingStatus;
import com.drms.disaster_relief.auth.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByEmail(String email);

    Page<Employee> findBySpecializationAndEmployeeStatus(EmployeeJob specialization,
                                                         EmployeeWorkingStatus availabilityStatus, Pageable pageable);
    Page<Employee> findByRole(RoleType roleType, Pageable page);
}
