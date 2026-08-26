package com.drms.disaster_relief.relief_camp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReliefCampRepository extends JpaRepository<ReliefCamp, UUID> {
}
