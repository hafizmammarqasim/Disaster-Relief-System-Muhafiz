package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProvinceRepository extends JpaRepository<Province, UUID> {

}
