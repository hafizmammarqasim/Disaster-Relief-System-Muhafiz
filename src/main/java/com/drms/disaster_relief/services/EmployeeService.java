package com.drms.disaster_relief.services;

import com.drms.disaster_relief.dto.Request.EmployeeSignUpRequest;
import com.drms.disaster_relief.entity.Auth;
import com.drms.disaster_relief.entity.Employee;
import com.drms.disaster_relief.enums.EmployeeWorkingStatus;
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
            auth.setEntityType("EMPLOYEE");
            auth.setEntityId(employee.getEmployeeId());
            auth.setLoginIdentifier(employeeDto.getLoginIdentifier());
            auth.setPassword(encoder.encode(employeeDto.getPassword()));
            auth.setRole(RoleType.valueOf(employeeDto.getRole()));
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
        employee.setRole(RoleType.valueOf(employeeDto.getRole()));
        employee.setEmail(employeeDto.getEmail());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setSpecialization(EmployeeSpecialization.valueOf(employeeDto.getSpecialization()));

        //If employee is Admin, no need for availability status....
        if(employeeDto.getRole().equalsIgnoreCase("Employee"))
            employee.setEmployeeStatus(EmployeeWorkingStatus.Available);
        else
            employee.setEmployeeStatus(null);
        employee.setActive(true);
        return employee;
    }

    public Optional<Employee> findByEmail(String email){
        return employeeRepo.findByEmail(email);
    }

    public Optional<Employee> findByIdentifier(UUID id){
        return employeeRepo.findById(id);
    }

}
