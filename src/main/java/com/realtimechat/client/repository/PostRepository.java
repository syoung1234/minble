package com.realtimechat.client.repository;

import java.util.List;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByMember(Member member);

}
