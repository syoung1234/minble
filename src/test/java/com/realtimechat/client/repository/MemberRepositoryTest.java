package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest extends TestBase {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    void 멤버등록() {
        // given
        Member member = createMember(10);

        // when
        Member result = memberRepository.save(member);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test10@test.com");
        assertThat(result.getRole()).isEqualTo(Role.ROLE_MEMBER);
    }


    @Test
    @Transactional
    void 멤버list등록() {
        // given
        for (int i = 100; i <= 200; i++) {
            memberRepository.save(createMember(i));
        }

        /* RoleType 변경은 AdminMemberService 에서 진행 */
        for (int i = 100; i <= 110; i++) {
            memberRepository.save(createStarMember(i));
        }

        // when
        List<Member> result = memberRepository.findAll();

        // then
        assertThat(result.size()).isGreaterThan(20);
    }


}