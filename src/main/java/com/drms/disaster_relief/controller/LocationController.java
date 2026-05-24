package com.drms.disaster_relief.controller;


import com.drms.disaster_relief.entity.City;
import com.drms.disaster_relief.entity.Province;
import com.drms.disaster_relief.repository.CityRepository;
import com.drms.disaster_relief.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/public/locations") // Public because users need this before they login or request help
public class LocationController {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private CityRepository cityRepository;

    // 1. Let user see all provinces
    @GetMapping("/provinces")
    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

    // 2. Let user see cities of a specific province
    @GetMapping("/cities/{provinceId}")
    public List<City> getCitiesByProvince(@PathVariable UUID provinceId) {
        // You'll need to add this method to your CityRepository
        return cityRepository.findByProvinceProvinceId(provinceId);
    }
}
