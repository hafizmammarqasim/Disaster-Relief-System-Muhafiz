package com.drms.disaster_relief.location.service;

import com.drms.disaster_relief.location.dto.BranchDto;
import com.drms.disaster_relief.location.dto.CityDTO;
import com.drms.disaster_relief.location.dto.ProvinceDTO;
import com.drms.disaster_relief.location.entity.Branch;
import com.drms.disaster_relief.location.entity.City;
import com.drms.disaster_relief.location.entity.Province;
import com.drms.disaster_relief.location.repo.BranchRepository;
import com.drms.disaster_relief.location.repo.CityRepository;
import com.drms.disaster_relief.location.repo.ProvinceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {
    private BranchRepository branchRepository;
    private CityRepository cityRepository;
    private ProvinceRepository provinceRepository;

    public LocationService(BranchRepository branchRepository,
                           CityRepository cityRepository,
                           ProvinceRepository provinceRepository){
        this.branchRepository = branchRepository;
        this.cityRepository = cityRepository;
        this.provinceRepository = provinceRepository;
    }

    @Transactional
    public String createProvince(ProvinceDTO provinceDTO) {
        Province province = new Province();
        province.setProvinceName(provinceDTO.getProvinceName());

        provinceRepository.save(province);

        return "Province: " + provinceDTO.getProvinceName() + " created successfully";
    }

    @Transactional
    public String createCity(CityDTO cityDTO) {

        Province province = provinceRepository.findById(cityDTO.getProvinceId()).orElseThrow(()-> new RuntimeException("Province with ID: " + cityDTO.getProvinceId() + " not found"));

        City city = new City();
        city.setCityName(cityDTO.getCityName());
        city.setProvince(province);
        cityRepository.save(city);

        return "City: " + cityDTO.getCityName() + " created successfully";
    }

    @Transactional
    public String createBranch(BranchDto branchDTO) {
        City city = cityRepository.findById(branchDTO.getCityId())
                .orElseThrow(() -> new RuntimeException("City with ID: " + branchDTO.getCityId() + " not found"));

        Branch branch = new Branch();
        branch.setBranchName(branchDTO.getBranchName());
        branch.setAddress(branchDTO.getAddress());
        branch.setPhoneNumber(branchDTO.getPhoneNumber());
        branch.setCity(city);

        branch = branchRepository.save(branch);

        return "Branch: " + branch.getBranchName() + " created successfully.";
    }
}
