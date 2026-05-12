package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {

    List<City> findByProvinceProvinceId(UUID provinceId);
}
