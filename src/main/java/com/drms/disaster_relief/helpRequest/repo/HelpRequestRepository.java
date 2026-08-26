package com.drms.disaster_relief.helpRequest.repo;

import com.drms.disaster_relief.helpRequest.entity.HelpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, UUID> {

    List<HelpRequest> findByUserUserId(UUID userId);

}
