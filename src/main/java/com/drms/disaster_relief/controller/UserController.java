package com.drms.disaster_relief.controller;

import com.drms.disaster_relief.dto.HelpRequestDTO;
import com.drms.disaster_relief.dto.HelpRequestResponseDTO;
import com.drms.disaster_relief.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-HelpRequest")
    public ResponseEntity<?> createHelpRequest(@RequestBody HelpRequestDTO helpRequestDTO) {
        return ResponseEntity.ok(userService.createHelpRequest(helpRequestDTO));
    }

    @GetMapping("/get-myRequests")
    public ResponseEntity<List<HelpRequestResponseDTO>> getMyRequests() {
        return ResponseEntity.ok(userService.getMyHelpRequests());
    }




}
