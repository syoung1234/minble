package com.minble.client.repository;

import java.util.List;
import java.util.Optional;

import com.minble.client.domain.Follow;
import com.minble.client.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByMemberOrderByCreatedAtDesc(Member member);

    Optional<Follow> findByFollowingAndMember(Member member, Member member2);

}
