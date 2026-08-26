package com.drms.disaster_relief.auth.repo;

import com.drms.disaster_relief.auth.entity.Auth;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@ReadingConverter
public interface AuthRepository extends JpaRepository<Auth, UUID> {

    Optional<Auth> findByLoginIdentifier(String loginIdentifier);

    boolean existsByLoginIdentifier(String identifier);

    Optional<Auth> findByEntityId(UUID loginIdentifier);
}
