package com.drms.disaster_relief.compensation.repo;

import com.drms.disaster_relief.compensation.entity.CompensationRequest;
import com.drms.disaster_relief.compensation.enums.CompensationRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CompensationRequestRepo extends JpaRepository<CompensationRequest, UUID> {

    long countByUser_UserId(UUID uuid);

    @Query("SELECT COALESCE() FROM CompensationRequest c WHERE c.user.userId = :userId AND c.Status = :status")
    float totalRemainingCompensationAmount(@Param("userId") UUID userId,
                                           @Param("status")CompensationRequestStatus status);
}
