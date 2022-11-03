package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    
}
