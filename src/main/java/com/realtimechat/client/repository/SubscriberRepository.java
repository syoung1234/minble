package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Subscriber;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    
}
