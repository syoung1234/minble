package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Long countByPost(Post post); // 해당 게시글 댓글 수
}
