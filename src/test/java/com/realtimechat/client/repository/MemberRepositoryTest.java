package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void 멤버등록() {
        // given
        final Member member = Member.builder()
                .email("test@test.com")
                .password(passwordEncoder.encode("1234"))
                .nickname("test")
                .profilePath("path")
                .role(Role.ROLE_MEMBER)
                .social(null)
                .emailConfirmation(false)
                .build();

        // when
        Member result = memberRepository.save(member);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@test.com");
        assertThat(result.getRole()).isEqualTo(Role.ROLE_MEMBER);
    }

}