package com.drms.disaster_relief.controller;

import com.drms.disaster_relief.dto.Request.CreateMissionDto;
import com.drms.disaster_relief.dto.Request.CrewAssignmentDto;
import com.drms.disaster_relief.dto.Response.MissionDispatchResponseDto;
import com.drms.disaster_relief.entity.Auth;
import com.drms.disaster_relief.entity.Mission;
import com.drms.disaster_relief.services.AuthService;
import com.drms.disaster_relief.services.MissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping("/missions")
public class MissionController {
        AuthService authService;
        MissionService missionService;

    public MissionController(AuthService authService, MissionService missionService) {
        this.authService = authService;
        this.missionService = missionService;
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

//    @PreAuthorize("hasRole(ADMIN)")
    @PostMapping("/create")
    public ResponseEntity<?> createMission(@RequestBody CreateMissionDto missionDto){

        Mission mission = missionService.createMission(missionDto);

        return new ResponseEntity<>(mission.getMissionId(), HttpStatus.OK);
    }

    @PostMapping("/assign-crew")
    public ResponseEntity<?> assignCrew(@RequestBody CrewAssignmentDto crewAssignmentDto){
        MissionDispatchResponseDto responseDto = missionService.crewAssignment(crewAssignmentDto);

        if(responseDto != null)
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        else
            return new ResponseEntity<>("Error in creating Mission", HttpStatus.NO_CONTENT);
    }

    private UUID getCurrentEmployeeId() {
        String identifier = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find Auth record
        Optional<Auth> authBox = authService.findByIdentifier(identifier);

        if (authBox.isPresent()) {
            Auth auth = authBox.get();
            // Get actual UUID!
            return auth.getEntityId();
        } else {
            return null;
        }

    }
}
