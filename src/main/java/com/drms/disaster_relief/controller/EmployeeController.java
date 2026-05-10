package com.drms.disaster_relief.controller;

import com.drms.disaster_relief.dto.EmployeeSignUpRequest;
import com.drms.disaster_relief.entity.Auth;
import com.drms.disaster_relief.entity.Employee;
import com.drms.disaster_relief.services.AuthService;
import com.drms.disaster_relief.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    EmployeeService employeeService;
    AuthService authService;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              AuthService authService){
        this.employeeService = employeeService;
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {

        String identifier = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find Auth record
        Optional<Auth> authBox = authService.findByIdentifier(identifier);

        if(authBox.isPresent()) {
            Auth auth = authBox.get();
            // Get actual UUID!
            UUID myEmployeeId = auth.getEntityId();

            // 4. Now safely fetch the Employee using the UUID
            Optional<Employee> employeeBox = employeeService.findByIdentifier(myEmployeeId);

            if(employeeBox.isPresent()){
                Employee employee = employeeBox.get();
                return new ResponseEntity<>(employee, HttpStatus.OK);
            } else{
                return new ResponseEntity<>("User not found in the registry",HttpStatus.NOT_FOUND);
            }

        } else{
            return new ResponseEntity<>("User was not registered", HttpStatus.NOT_FOUND);
        }
    }

    //This approach isn't good right now without specifying roles for this...
    //Otherwise user can get the record of any person by just sending any person's email or identifier
    //from their own account... This is just ensuring authentication right now, not authorization
    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeSignUpRequest employeeDto){

        if(employeeService.employeeSignUp(employeeDto))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(false, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getEmployee(@PathVariable String email){
        Optional<Employee> employeeBox = employeeService.findByEmail(email);

        if(employeeBox.isPresent()){
            return new ResponseEntity<>(employeeBox.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Employee not Found", HttpStatus.NOT_FOUND);
        }
    }


}
