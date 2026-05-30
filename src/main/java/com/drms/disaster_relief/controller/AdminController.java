package com.drms.disaster_relief.controller;

import com.drms.disaster_relief.dto.CityDTO;
import com.drms.disaster_relief.dto.EmployeeDTO;
import com.drms.disaster_relief.dto.ProvinceDTO;
import com.drms.disaster_relief.dto.Request.EmployeeSignUpRequest;
import com.drms.disaster_relief.entity.NGO;
import com.drms.disaster_relief.services.AdminService;
import com.drms.disaster_relief.services.AuthService;
import com.drms.disaster_relief.services.EmployeeService;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AdminService adminService;

    private EmployeeService employeeService;

    @PostMapping("/create-employee")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeSignUpRequest request) {

        System.out.println("Admin is creating an Employee....!!!!");
        if( employeeService.employeeSignUp(request))
         return new ResponseEntity<>("Success in creating employee", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Could not create Employee", HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("/activate-NGO/{id}")
    public ResponseEntity<?> activateNGO(@PathVariable UUID id) {
        return ResponseEntity.ok(authService.verifyNGO(id));
    }

    @GetMapping("/pending-NGOs")
    public ResponseEntity<?> getPendingNGOs() {
        List<NGO> pendingNgoList = authService.getPendingNGOs();
        if (pendingNgoList.isEmpty()) {
            return ResponseEntity.ok("No pending NGO at this time");
        }
        return ResponseEntity.ok(pendingNgoList);
    }


    @PostMapping("create-province")
    public ResponseEntity<?> createProvince(@RequestBody ProvinceDTO provinceDTO) {
        return ResponseEntity.ok(adminService.createProvince(provinceDTO));
    }

    @PostMapping("create-city")
    public ResponseEntity<?> createCity(@RequestBody CityDTO cityDTO) {
        return ResponseEntity.ok(adminService.createCity(cityDTO));
    }




}

