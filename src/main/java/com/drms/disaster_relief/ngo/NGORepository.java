package com.drms.disaster_relief.ngo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NGORepository extends JpaRepository<NGO, UUID> {

    List<NGO> findAllByIsActiveFalse();
}
