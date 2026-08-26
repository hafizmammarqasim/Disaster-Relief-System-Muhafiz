package com.drms.disaster_relief.auth.service;

import com.drms.disaster_relief.location.repo.BranchRepository;
import com.drms.disaster_relief.location.repo.CityRepository;
import com.drms.disaster_relief.location.repo.ProvinceRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;
    private final BranchRepository branchRepository; // Added Branch Repo

    public AdminService(ProvinceRepository provinceRepository, CityRepository cityRepository, BranchRepository branchRepository) {
        this.provinceRepository = provinceRepository;
        this.cityRepository = cityRepository;
        this.branchRepository = branchRepository;
    }
}

