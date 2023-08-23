package com.minble.client.repository;


import com.minble.client.domain.Comment;
import com.minble.client.domain.Member;
import com.minble.client.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Long countByPost(Post post); // 해당 게시글 댓글 수

    Page<Comment> findByPostAndDepth(Post post, Integer depth, Pageable pageable);

    Page<Comment> findByMember(Member member, Pageable pageable);

    Page<Comment> findByParentId(Integer parentId, Pageable pageable);

    Page<Comment> findByPostIdAndDepth(Integer postId, Integer depth, Pageable pageable);

    Optional<Comment> findByIdAndMember(Integer id, Member member);

}
