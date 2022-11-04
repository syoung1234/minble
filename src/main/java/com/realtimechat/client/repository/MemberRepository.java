package com.realtimechat.client.repository;

import java.util.Optional;

import com.realtimechat.client.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    
    Optional<Member> findByEmail(String email);

    Member findByNickname(String nickname);
}
