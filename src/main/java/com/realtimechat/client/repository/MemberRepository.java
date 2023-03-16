package com.realtimechat.client.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndSocial(String email, String social);

    Optional<Member> findByEmailAndSocialAndEmailConfirmation(String email, String social, Boolean emailConfirmation);

    Optional<Member> findByNickname(String nickname);

    List<Member> findByRoleOrderByCreatedAtDesc(Role role);

    @Query("SELECT m FROM Member m WHERE m.role= :role AND m.id NOT IN :followingList")
    List<Member> findByRoleAndMemberNotIn(@Param("role") Role role, @Param("followingList") List<UUID> followingList);

    // 닉네임 검색
    Page<Member> findByNicknameContaining(String keyword, Pageable pageable);

    // 이메일 검색
    Page<Member> findByEmailContaining(String keyword, Pageable pageable);

    // 회원유형 검색
    Page<Member> findByRole(Role role, Pageable pageable);
}
