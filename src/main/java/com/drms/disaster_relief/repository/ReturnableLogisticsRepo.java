package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.ReturnableLogistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReturnableLogisticsRepo extends JpaRepository<ReturnableLogistics, UUID> {
}
