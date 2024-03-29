package com.minble.client.repository;

import java.util.List;

import com.minble.client.domain.Member;
import com.minble.client.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer>, PostRepositoryCustom {

    List<Post> findByMember(Member member);

    @Query("SELECT p FROM Post p WHERE p.member in :memberList ORDER BY CREATED_AT DESC")
    Page<Post> findByMemberOrderByCreatedAtDesc(@Param("memberList") List<Member> memberList, Pageable pageable);

}
