package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Favorite;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Optional<Favorite> findByMemberAndPost(Member member, Post post);

    Long countByPost(Post post); // 해당 게시글 좋아요 수
}
