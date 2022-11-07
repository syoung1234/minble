package com.realtimechat.client.repository;

import java.util.List;

import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.dto.response.FollowResponseDto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<FollowResponseDto> findByMember(Member member);
}
