package com.minble.client.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.minble.client.domain.Member;
import com.minble.client.domain.Role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndSocial(String email, String social);

    Optional<Member> findByEmailAndSocialNot(String email, String social);

    Optional<Member> findByEmailAndSocialAndEmailConfirmation(String email, String social, Boolean emailConfirmation);

    Optional<Member> findByNickname(String nickname);

    List<Member> findByRoleOrderByCreatedAtDesc(Role role);

    // STAR or STAR_TEST
    List<Member> findByRoleOrRoleOrderByCreatedAtDesc(Role star, Role starTest);

    @Query("SELECT m FROM Member m WHERE m.role= :role AND m.id NOT IN :followingList")
    List<Member> findByRoleAndMemberNotIn(@Param("role") Role role, @Param("followingList") List<UUID> followingList);

    @Query("SELECT m FROM Member m WHERE (m.role= :role OR m.role= :roleTest) AND m.id NOT IN :followingList")
    List<Member> findByRoleOrRoleAndMemberNotIn(@Param("role") Role role, @Param("roleTest") Role roleTest, @Param("followingList") List<UUID> followingList);

    // 닉네임 검색
    Page<Member> findByNicknameContaining(String keyword, Pageable pageable);

    // 이메일 검색
    Page<Member> findByEmailContaining(String keyword, Pageable pageable);

    // 회원유형 검색
    Page<Member> findByRole(Role role, Pageable pageable);
}
