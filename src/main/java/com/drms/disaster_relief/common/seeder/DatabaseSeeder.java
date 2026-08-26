package com.drms.disaster_relief.common.seeder;

import com.drms.disaster_relief.auth.entity.Auth;
import com.drms.disaster_relief.auth.enums.EmployeeJob;
import com.drms.disaster_relief.auth.enums.EmployeeWorkingStatus;
import com.drms.disaster_relief.auth.enums.EntityType;
import com.drms.disaster_relief.auth.enums.RoleType;
import com.drms.disaster_relief.auth.repo.AuthRepository;
import com.drms.disaster_relief.location.entity.Branch;
import com.drms.disaster_relief.location.entity.City;
import com.drms.disaster_relief.location.entity.Province;
import com.drms.disaster_relief.location.repo.BranchRepository;
import com.drms.disaster_relief.location.repo.CityRepository;
import com.drms.disaster_relief.auth.repo.EmployeeRepository;
import com.drms.disaster_relief.auth.entity.Employee;
import com.drms.disaster_relief.location.repo.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
            createEmployee("Ammar", "Admin", "ammar.admin", "111", EmployeeJob.SAR_SPECIALIST, branch);

            // Drivers
            createEmployee("Ali", "Khan", "ammar121", "121", EmployeeJob.DRIVER, branch);
            createEmployee("Abu", "Bakr", "ammar122", "122", EmployeeJob.DRIVER, branch);
            createEmployee("Umer", "Farooq", "ammar123", "123", EmployeeJob.DRIVER, branch);
            createEmployee("Usman", "Ghani", "ammar124", "124", EmployeeJob.DRIVER, branch);

            // Medics
            createEmployee("Zain", "Ali", "ammar125", "125", EmployeeJob.MEDIC, branch);
            createEmployee("Hassan", "Ahmed", "ammar126", "126", EmployeeJob.MEDIC, branch);
            createEmployee("Sara", "Khan", "ammar127", "127", EmployeeJob.MEDIC, branch);
            createEmployee("Fatima", "Noor", "ammar128", "128", EmployeeJob.MEDIC, branch);

            // Firefighters
            createEmployee("Hamza", "Sheikh", "ammar129", "129", EmployeeJob.FIREFIGHTER, branch);
            createEmployee("Bilal", "Ahmed", "ammar130", "130", EmployeeJob.FIREFIGHTER, branch);
            createEmployee("Saad", "Rafiq", "ammar131", "131", EmployeeJob.FIREFIGHTER, branch);
            createEmployee("Talha", "Javed", "ammar132", "132", EmployeeJob.FIREFIGHTER, branch);

            // Divers
            createEmployee("Waqas", "Ali", "ammar133", "133", EmployeeJob.DIVER, branch);
            createEmployee("Omer", "Shah", "ammar134", "134", EmployeeJob.DIVER, branch);

            // SAR Specialists
            createEmployee("Kashif", "Anwar", "ammar137", "137", EmployeeJob.SAR_SPECIALIST, branch);
            createEmployee("Junaid", "Khan", "ammar138", "138", EmployeeJob.SAR_SPECIALIST, branch);

            System.out.println("✅ Seeding Complete! 20 Employees and Base Metadata generated.");
        } else {
            System.out.println("⏩ Database already has data. Skipping seeder.");
        }
    }

    // Helper method to create Employee + Auth quickly
    private void createEmployee(String first, String last, String identifier, String cnic, EmployeeJob spec, Branch branch) {
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
        auth.setEntityType(EntityType.EMPLOYEE);
        auth.setEntityId(savedEmp.getEmployeeId());
        auth.setActive(true);
        authRepository.save(auth);
    }
}
