package com.realtimechat.client.repository;


import java.util.List;

import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Long countByPost(Post post); // 해당 게시글 댓글 수

    Page<Comment> findByPost(Post post, Pageable pageable);

    List<Comment> findByMember(Member member);
}
