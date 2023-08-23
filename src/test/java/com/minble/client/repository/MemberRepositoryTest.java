package com.minble.client.repository;

import com.minble.client.domain.Member;
import com.minble.client.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest extends TestBase {
    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    protected PasswordEncoder passwordEncoder;

    @Test
    public void MemberRepositoryIsNotNull() {
        assertThat(memberRepository).isNotNull();
    }

    @Test
    void 멤버등록() {
        // given
        Member member = Member.builder()
                .email("test10@test.com")
                .password(passwordEncoder.encode("1234"))
                .nickname("test10")
                .profilePath("test")
                .role(Role.ROLE_MEMBER)
                .social(null)
                .emailConfirmation(true)
                .build();

        // when
        Member result = memberRepository.save(member);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test10@test.com");
        assertThat(result.getRole()).isEqualTo(Role.ROLE_MEMBER);
    }

}