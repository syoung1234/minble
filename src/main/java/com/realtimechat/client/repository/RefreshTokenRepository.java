package com.realtimechat.client.repository;

import com.realtimechat.client.domain.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    
}
