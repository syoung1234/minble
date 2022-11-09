package com.realtimechat.client.repository;

import java.util.List;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByMember(Member member);

    @Query("SELECT p FROM Post p WHERE p.member in :memberList")
    List<Post> findByMember(@Param("memberList") List<Member> memberList);

}
