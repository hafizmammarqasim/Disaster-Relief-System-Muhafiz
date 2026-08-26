package com.drms.disaster_relief.auth.service;

import com.drms.disaster_relief.auth.dto.request.EmployeeSignUpRequest;
import com.drms.disaster_relief.auth.dto.request.UpdateEmployeeRequestDto;
import com.drms.disaster_relief.auth.dto.request.UpdateMyProfileRequestDto;
import com.drms.disaster_relief.auth.dto.response.EmployeeManagementResponseDto;
import com.drms.disaster_relief.auth.dto.response.EmployeeDetailsResponseDto;
import com.drms.disaster_relief.auth.entity.Auth;
import com.drms.disaster_relief.auth.entity.Employee;
import com.drms.disaster_relief.auth.enums.EmployeeWorkingStatus;
import com.drms.disaster_relief.auth.enums.EntityType;
import com.drms.disaster_relief.auth.enums.RoleType;
import com.drms.disaster_relief.auth.repo.EmployeeRepository;
import com.drms.disaster_relief.auth.enums.EmployeeJob;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    private static EmployeeJob parseSpecialization(String specialization) {
        if (specialization == null || specialization.isBlank()) {
            throw new IllegalArgumentException("specialization is required");
        }
        for (EmployeeJob value : EmployeeJob.values()) {
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

    public List<EmployeeManagementResponseDto> getEmployeeList() throws IllegalStateException {
        PageRequest page = PageRequest.of(1,10);
        Page<Employee> employeePage = employeeRepo.findByRole(RoleType.EMPLOYEE, page);

        List<EmployeeManagementResponseDto> responseList = new ArrayList<>();
        for (Employee emp : employeePage.getContent()) {
            EmployeeManagementResponseDto dto = new EmployeeManagementResponseDto();
            dto.setEmployeeId(emp.getEmployeeId());
            dto.setFullName(emp.getFirstName() + " " + emp.getLastName());
            dto.setSpecialization(emp.getSpecialization());
            dto.setCurrentStatus(emp.getEmployeeStatus());
            responseList.add(dto);
        }
        return responseList;
    }

    public EmployeeDetailsResponseDto getEmployeeDetails(UUID employeeId) {
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("Employee not found"));
        return convertToDetailedDTO(emp);
    }

    @Transactional
    public EmployeeDetailsResponseDto adminUpdateEmployee(UUID employeeId, UpdateEmployeeRequestDto dto) {
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("Employee not found"));

        Auth auth = authService.findByEntityId(emp.getEmployeeId())
                .orElseThrow(()-> new IllegalStateException("Auth not found"));

        if (dto.getFirstName() != null) emp.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) emp.setLastName(dto.getLastName());
        if (dto.getEmail() != null) emp.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) emp.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getRole() != null) {
            emp.setRole(dto.getRole());
            auth.setRole(dto.getRole());
        }
        if (dto.getSpecialization() != null) emp.setSpecialization(dto.getSpecialization());
        if (dto.getEmployeeStatus() != null) emp.setEmployeeStatus(dto.getEmployeeStatus());

        employeeRepo.save(emp);
        authService.saveAuth(auth);

        return convertToDetailedDTO(emp);
    }

    @Transactional
    public EmployeeDetailsResponseDto selfUpdateProfile(UUID myEmployeeId, UpdateMyProfileRequestDto dto) {
        Employee emp = employeeRepo.findById(myEmployeeId)
                .orElseThrow(() -> new IllegalStateException("Profile not found"));

        if (dto.getEmail() != null) emp.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) emp.setPhoneNumber(dto.getPhoneNumber());

        employeeRepo.save(emp);
        return convertToDetailedDTO(emp);
    }

    // Helper Method
    private EmployeeDetailsResponseDto convertToDetailedDTO(Employee emp) {
        EmployeeDetailsResponseDto dto = new EmployeeDetailsResponseDto();
        dto.setEmployeeId(emp.getEmployeeId());
        dto.setFirstName(emp.getFirstName());
        dto.setLastName(emp.getLastName());
        dto.setCnic(emp.getCnic());
        dto.setEmail(emp.getEmail());
        dto.setPhoneNumber(emp.getPhoneNumber());
        dto.setRole(emp.getRole());
        dto.setSpecialization(emp.getSpecialization());
        dto.setEmployeeStatus(emp.getEmployeeStatus());
        if (emp.getBranch() != null) {
            dto.setBranchName(emp.getBranch().getBranchName());
        }
        return dto;
    }

    public Optional<Employee> findByEmail(String email){
        return employeeRepo.findByEmail(email);
    }

    public Optional<Employee> findByIdentifier(UUID id){
        return employeeRepo.findById(id);
    }

}
