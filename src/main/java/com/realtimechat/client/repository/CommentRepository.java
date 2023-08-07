package com.realtimechat.client.repository;


import com.realtimechat.client.domain.Comment;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Long countByPost(Post post); // 해당 게시글 댓글 수

    Page<Comment> findByPostAndDepth(Post post, Integer depth, Pageable pageable);

    Page<Comment> findByMember(Member member, Pageable pageable);

    Page<Comment> findByParentId(Integer parentId, Pageable pageable);

    Page<Comment> findByPostId(Integer postId, Pageable pageable);

}
