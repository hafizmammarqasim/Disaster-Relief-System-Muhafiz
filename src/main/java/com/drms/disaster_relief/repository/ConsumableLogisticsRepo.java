package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.ConsumableLogistics;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ConsumableLogisticsRepo extends JpaRepository<ConsumableLogistics, UUID> {
    // Fetch branch-specific consumable inventory
    List<ConsumableLogistics> findByBranch_BranchId(UUID branchId);
}
