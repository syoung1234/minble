package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    
}
