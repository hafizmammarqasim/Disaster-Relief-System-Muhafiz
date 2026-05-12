package com.drms.disaster_relief.services;

import com.drms.disaster_relief.dto.CityDTO;
import com.drms.disaster_relief.dto.ProvinceDTO;
import com.drms.disaster_relief.entity.City;
import com.drms.disaster_relief.entity.Province;
import com.drms.disaster_relief.repository.CityRepository;
import com.drms.disaster_relief.repository.ProvinceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;

    public AdminService(ProvinceRepository provinceRepository, CityRepository cityRepository) {
        this.provinceRepository = provinceRepository;
        this.cityRepository = cityRepository;
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


}
