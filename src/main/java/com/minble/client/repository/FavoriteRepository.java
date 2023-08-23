package com.minble.client.repository;

import com.minble.client.domain.Favorite;
import com.minble.client.domain.Member;
import com.minble.client.domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Optional<Favorite> findByMemberAndPost(Member member, Post post);


    Long countByPost(Post post); // 해당 게시글 좋아요 수


    Optional<Favorite> findByMemberAndPostId(Member member, int postId);
}
