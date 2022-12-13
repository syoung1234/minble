package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    
    Page<Reply> findByCommentOrderByCreatedAt(Comment comment, Pageable pageable);
}
