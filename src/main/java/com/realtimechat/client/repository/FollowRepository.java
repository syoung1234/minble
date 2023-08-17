package com.realtimechat.client.repository;

import java.util.List;
import java.util.Optional;

import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.dto.response.FollowResponseDto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByMemberOrderByCreatedAtDesc(Member member);

    Optional<Follow> findByFollowingAndMember(Member member, Member member2);

}
