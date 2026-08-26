package com.drms.disaster_relief.relief_camp;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReliefCampController {

    private ReliefCampService reliefCampService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/camp/manage")
    public ResponseEntity<?> createReliefCamp(ReliefCampDto reliefCampDto){
        reliefCampService.createReliefCamp(reliefCampDto);
        return new ResponseEntity<>();
    }
}
