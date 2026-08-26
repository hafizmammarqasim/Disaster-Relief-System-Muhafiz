package com.drms.disaster_relief.location.controller;

import com.drms.disaster_relief.location.dto.BranchDto;
import com.drms.disaster_relief.location.dto.CityDTO;
import com.drms.disaster_relief.location.dto.ProvinceDTO;
import com.drms.disaster_relief.location.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class LocationController {

    private LocationService locationService;
    @PostMapping("/province/manage")
    public ResponseEntity<?> createProvince(@RequestBody ProvinceDTO provinceDTO) {
        return ResponseEntity.ok(locationService.createProvince(provinceDTO));
    }

    @PostMapping("/city/manage")
    public ResponseEntity<?> createCity(@RequestBody CityDTO cityDTO) {
        return ResponseEntity.ok(locationService.createCity(cityDTO));
    }

    @PostMapping("/branch/manage")
    public ResponseEntity<?> createBranch(@RequestBody BranchDto branchDTO) {
        return ResponseEntity.ok(locationService.createBranch(branchDTO));
    }
}
