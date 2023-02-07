package com.realtimechat.client.repository;

import com.realtimechat.client.domain.MessageFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageFileRepository extends JpaRepository<MessageFile, Integer> {
    
}
