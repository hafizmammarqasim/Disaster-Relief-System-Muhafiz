package com.drms.disaster_relief.services;

import com.drms.disaster_relief.dto.Request.EmployeeSignUpRequest;
import com.drms.disaster_relief.entity.Auth;
import com.drms.disaster_relief.entity.Employee;
import com.drms.disaster_relief.enums.EmployeeWorkingStatus;
import com.drms.disaster_relief.enums.EntityType;
import com.drms.disaster_relief.enums.RoleType;
import com.drms.disaster_relief.repository.EmployeeRepository;
import com.drms.disaster_relief.enums.EmployeeSpecialization;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {
    EmployeeRepository employeeRepo;
    AuthService authService;
    PasswordEncoder encoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepo,
                           AuthService authService,
                           PasswordEncoder encoder){
        this.employeeRepo = employeeRepo;
        this.authService = authService;
        this.encoder = encoder;
    }


    @Transactional
    public boolean employeeSignUp(EmployeeSignUpRequest employeeDto){

        if(authService.isUnique(employeeDto.getLoginIdentifier())) {
            Employee employee = getEmployee(employeeDto);
            employeeRepo.save(employee);

            Auth auth = new Auth();
            //to get back the employee using switch case
            auth.setEntityType(EntityType.EMPLOYEE);
            auth.setEntityId(employee.getEmployeeId());
            auth.setLoginIdentifier(employeeDto.getLoginIdentifier());
            auth.setPassword(encoder.encode(employeeDto.getPassword()));
            auth.setRole(employee.getRole());
            auth.setActive(true);
            authService.saveAuth(auth);
            return true;

        }

        return false;

    }

    private static Employee getEmployee(EmployeeSignUpRequest employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setBranch(employeeDto.getBranch());
        employee.setCnic(employeeDto.getCnic());
        RoleType role = parseRole(employeeDto.getRole());
        employee.setRole(role);
        employee.setEmail(employeeDto.getEmail());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setSpecialization(parseSpecialization(employeeDto.getSpecialization()));

        EmployeeWorkingStatus status = parseWorkingStatus(employeeDto.getAvailabilityStatus());
        if (status == null && role == RoleType.EMPLOYEE) {
            status = EmployeeWorkingStatus.Available;
        }
        employee.setEmployeeStatus(status);
        employee.setActive(true);
        return employee;
    }

    private static RoleType parseRole(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("role is required");
        }
        for (RoleType value : RoleType.values()) {
            if (value.name().equalsIgnoreCase(role.trim())) {
                return value;
            }
        }
        throw new IllegalArgumentException("invalid role: " + role);
    }

    private static EmployeeSpecialization parseSpecialization(String specialization) {
        if (specialization == null || specialization.isBlank()) {
            throw new IllegalArgumentException("specialization is required");
        }
        for (EmployeeSpecialization value : EmployeeSpecialization.values()) {
            if (value.name().equalsIgnoreCase(specialization.trim())) {
                return value;
            }
        }
        throw new IllegalArgumentException("invalid specialization: " + specialization);
    }

    private static EmployeeWorkingStatus parseWorkingStatus(String availabilityStatus) {
        if (availabilityStatus == null || availabilityStatus.isBlank()) {
            return null;
        }
        for (EmployeeWorkingStatus value : EmployeeWorkingStatus.values()) {
            if (value.name().equalsIgnoreCase(availabilityStatus.trim())) {
                return value;
            }
        }
        throw new IllegalArgumentException("invalid availabilityStatus: " + availabilityStatus);
    }

    public Optional<Employee> findByEmail(String email){
        return employeeRepo.findByEmail(email);
    }

    public Optional<Employee> findByIdentifier(UUID id){
        return employeeRepo.findById(id);
    }

}
