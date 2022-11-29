package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    
}
