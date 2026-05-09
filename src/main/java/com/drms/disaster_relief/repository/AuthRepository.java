package com.drms.disaster_relief.repository;

import com.drms.disaster_relief.entity.Auth;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@ReadingConverter
public interface AuthRepository extends JpaRepository<Auth, UUID> {

    Optional<Auth> findByLoginIdentifier(String loginIdentifier);
}
