package com.minble.client.repository;

import com.minble.client.domain.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    
}
