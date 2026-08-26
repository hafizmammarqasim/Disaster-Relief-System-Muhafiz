package com.drms.disaster_relief.location.repo;

import com.drms.disaster_relief.location.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, UUID> {
}
