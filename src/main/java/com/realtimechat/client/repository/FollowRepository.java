package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Follow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    
}
