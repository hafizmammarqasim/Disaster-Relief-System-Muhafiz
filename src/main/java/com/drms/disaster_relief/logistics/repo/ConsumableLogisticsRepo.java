package com.drms.disaster_relief.logistics.repo;

import com.drms.disaster_relief.logistics.entity.ConsumableLogistics;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ConsumableLogisticsRepo extends JpaRepository<ConsumableLogistics, UUID> {
    // Fetch branch-specific consumable inventory
    List<ConsumableLogistics> findByBranch_BranchId(UUID branchId);
}
