package com.drms.disaster_relief.seeder;

import com.drms.disaster_relief.entity.*;
import com.drms.disaster_relief.enums.*;
import com.drms.disaster_relief.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private AuthRepository authRepository;
    @Autowired private ProvinceRepository provinceRepository;
    @Autowired private CityRepository cityRepository;
    @Autowired private BranchRepository branchRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Only run this if the database is completely empty!
        if (employeeRepository.count() == 0) {
            System.out.println("🌱 Database is empty! Seeding initial data...");

            // 1. Create Metadata (Province, City, Branch)
            Province province = new Province();
            province.setProvinceName("Punjab");
            province = provinceRepository.save(province);

            City city = new City();
            city.setCityName("Lahore");
            city.setProvince(province);
            city = cityRepository.save(city);

            Branch branch = new Branch();
            branch.setBranchName("Lahore HQ");
            branch.setCity(city);
            branch = branchRepository.save(branch);

            // 2. Create Employees
            createEmployee("Ammar", "Admin", "ammar.admin", "111", EmployeeSpecialization.SAR_SPECIALIST, branch);

            // Drivers
            createEmployee("Ali", "Khan", "ammar121", "121", EmployeeSpecialization.DRIVER, branch);
            createEmployee("Abu", "Bakr", "ammar122", "122", EmployeeSpecialization.DRIVER, branch);
            createEmployee("Umer", "Farooq", "ammar123", "123", EmployeeSpecialization.DRIVER, branch);
            createEmployee("Usman", "Ghani", "ammar124", "124", EmployeeSpecialization.DRIVER, branch);

            // Medics
            createEmployee("Zain", "Ali", "ammar125", "125", EmployeeSpecialization.MEDIC, branch);
            createEmployee("Hassan", "Ahmed", "ammar126", "126", EmployeeSpecialization.MEDIC, branch);
            createEmployee("Sara", "Khan", "ammar127", "127", EmployeeSpecialization.MEDIC, branch);
            createEmployee("Fatima", "Noor", "ammar128", "128", EmployeeSpecialization.MEDIC, branch);

            // Firefighters
            createEmployee("Hamza", "Sheikh", "ammar129", "129", EmployeeSpecialization.FIREFIGHTER, branch);
            createEmployee("Bilal", "Ahmed", "ammar130", "130", EmployeeSpecialization.FIREFIGHTER, branch);
            createEmployee("Saad", "Rafiq", "ammar131", "131", EmployeeSpecialization.FIREFIGHTER, branch);
            createEmployee("Talha", "Javed", "ammar132", "132", EmployeeSpecialization.FIREFIGHTER, branch);

            // Divers
            createEmployee("Waqas", "Ali", "ammar133", "133", EmployeeSpecialization.DIVER, branch);
            createEmployee("Omer", "Shah", "ammar134", "134", EmployeeSpecialization.DIVER, branch);

            // SAR Specialists
            createEmployee("Kashif", "Anwar", "ammar137", "137", EmployeeSpecialization.SAR_SPECIALIST, branch);
            createEmployee("Junaid", "Khan", "ammar138", "138", EmployeeSpecialization.SAR_SPECIALIST, branch);

            System.out.println("✅ Seeding Complete! 20 Employees and Base Metadata generated.");
        } else {
            System.out.println("⏩ Database already has data. Skipping seeder.");
        }
    }

    // Helper method to create Employee + Auth quickly
    private void createEmployee(String first, String last, String identifier, String cnic, EmployeeSpecialization spec, Branch branch) {
        // 1. Save Employee
        Employee emp = new Employee();
        emp.setFirstName(first);
        emp.setLastName(last);
        emp.setEmail(identifier + "@test.com"); // Email
        emp.setCnic(cnic);
        emp.setPhoneNumber("0300" + cnic);
        emp.setRole(RoleType.EMPLOYEE);
        emp.setSpecialization(spec);
        emp.setEmployeeStatus(EmployeeWorkingStatus.Available);
        emp.setBranch(branch);
        emp.setActive(true);
        Employee savedEmp = employeeRepository.save(emp);

        // 2. Save Auth
        Auth auth = new Auth();
        auth.setLoginIdentifier(identifier); // Login ID (e.g. ammar121)
        auth.setPassword(passwordEncoder.encode("123")); // Password is 123
        auth.setRole(RoleType.EMPLOYEE);
        auth.setEntityType("EMPLOYEE");
        auth.setEntityId(savedEmp.getEmployeeId());
        auth.setActive(true);
        authRepository.save(auth);
    }
}
