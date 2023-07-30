package com.realtimechat.client.repository;

import com.realtimechat.client.domain.RefreshToken;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    
}
