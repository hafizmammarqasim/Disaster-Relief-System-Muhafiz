package com.drms.disaster_relief.controller;

import com.drms.disaster_relief.dto.Request.EmployeeSignUpRequest;
import com.drms.disaster_relief.entity.Auth;
import com.drms.disaster_relief.entity.Employee;
import com.drms.disaster_relief.entity.Mission;
import com.drms.disaster_relief.services.AuthService;
import com.drms.disaster_relief.services.EmployeeService;
import com.drms.disaster_relief.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    EmployeeService employeeService;
    AuthService authService;
    MissionService missionService;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              AuthService authService,
                              MissionService missionService){
        this.employeeService = employeeService;
        this.authService = authService;
        this.missionService = missionService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {

            UUID employeeId = getCurrentEmployeeId();

            if(employeeId!=null) {
                Optional<Employee> employeeBox = employeeService.findByIdentifier(employeeId);
                if (employeeBox.isPresent()) {
                    Employee employee = employeeBox.get();
                    return new ResponseEntity<>(employee, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("User not found in the registry", HttpStatus.NOT_FOUND);
                }
            } else{
                return new ResponseEntity<>("User not registered", HttpStatus.NOT_FOUND);
            }

    }

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeSignUpRequest employeeDto){

        if(employeeService.employeeSignUp(employeeDto))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(false, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/my-missions")
    public ResponseEntity<?> viewMissions(){

        UUID myEmployeeId = getCurrentEmployeeId();
        List<Mission> missions = missionService.getMissionByEmployee(myEmployeeId);
        //in case of empty list...
        if(missions.isEmpty()){
            return new ResponseEntity<>("No missions to show", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(missions, HttpStatus.OK);
    }

    //This approach isn't good right now without specifying roles for this...
    //Otherwise user can get the record of any person by just sending any person's email or identifier
    //from their own account... This is just ensuring authentication right now, not authorization
    @GetMapping("/{email}")
    public ResponseEntity<?> getEmployee(@PathVariable String email){
        Optional<Employee> employeeBox = employeeService.findByEmail(email);

        if(employeeBox.isPresent()){
            return new ResponseEntity<>(employeeBox.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Employee not Found", HttpStatus.NOT_FOUND);
        }
    }

    private UUID getCurrentEmployeeId() {
        String identifier = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find Auth record
        Optional<Auth> authBox = authService.findByIdentifier(identifier);

        if (authBox.isPresent()) {
            Auth auth = authBox.get();
            // Get actual UUID!
            UUID myEmployeeId = auth.getEntityId();
            return myEmployeeId;
        } else {
            return null;
        }

    }

}
