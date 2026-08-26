package com.drms.disaster_relief.helpRequest.repo;

import com.drms.disaster_relief.helpRequest.entity.HelpRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HelpRequestLogRepository extends JpaRepository<HelpRequestLog, UUID> {

}
