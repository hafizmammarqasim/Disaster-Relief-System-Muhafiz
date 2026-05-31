package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.ReturnableLogistics;
import com.drms.disaster_relief.enums.LogisticsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReturnableLogisticsRepo extends JpaRepository<ReturnableLogistics, UUID> {
    long countByStatus(LogisticsStatus status);

    // 1. Count available for the frontend catalog
    long countByProductInfo_ProductIdAndBranch_BranchIdAndStatus(UUID productId, UUID branchId, LogisticsStatus status);

    // 2. Fetch specific available items dynamically for assignment
    Page<ReturnableLogistics> findByProductInfo_ProductIdAndStatus(UUID productId, LogisticsStatus status, Pageable pageable);
}
