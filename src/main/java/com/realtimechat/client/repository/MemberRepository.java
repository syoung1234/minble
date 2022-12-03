package com.realtimechat.client.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, String> {
    
    Optional<Member> findByEmail(String email);

    Member findByNickname(String nickname);

    List<Member> findByRole(Role role);

    @Query("SELECT m FROM Member m WHERE m.role= :role AND m.id NOT IN :followingList")
    List<Member> findByRoleAndMemberNotIn(@Param("role") Role role, @Param("followingList") List<UUID> followingList);
}
