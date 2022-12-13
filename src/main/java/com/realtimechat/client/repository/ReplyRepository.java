package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Reply;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    
}
