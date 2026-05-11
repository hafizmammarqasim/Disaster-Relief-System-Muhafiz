package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {


}
