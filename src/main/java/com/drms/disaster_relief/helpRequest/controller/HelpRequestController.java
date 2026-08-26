package com.drms.disaster_relief.helpRequest.controller;

import com.drms.disaster_relief.helpRequest.dto.HelpRequestDTO;
import com.drms.disaster_relief.helpRequest.dto.HelpRequestResponseDTO;
import com.drms.disaster_relief.helpRequest.service.HelpRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/help")
public class HelpRequestController {

    @Autowired
    private HelpRequestService helpRequestService;

    @PostMapping("/create-HelpRequest")
    public ResponseEntity<?> createHelpRequest(@RequestBody HelpRequestDTO helpRequestDTO) {
        return ResponseEntity.ok(helpRequestService.createHelpRequest(helpRequestDTO));
    }

    @GetMapping("/get-myRequests")
    public ResponseEntity<List<HelpRequestResponseDTO>> getMyRequests() {
        return ResponseEntity.ok(helpRequestService.getMyHelpRequests());
    }
}
