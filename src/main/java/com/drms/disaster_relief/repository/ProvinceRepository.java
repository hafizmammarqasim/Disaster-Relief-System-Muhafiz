package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, UUID> {

}

