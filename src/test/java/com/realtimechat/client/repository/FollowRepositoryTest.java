package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FollowRepositoryTest extends TestBase {

    @Autowired
    private FollowRepository followRepository;

    @DisplayName("팔로우 하기")
    @Test
    void save() {
        // given
        Member member = new Member();
        member.setId(UUID.randomUUID());

        // when
        for (int i = 10; i <= 15; i++) {
            Member star = new Member();
            star.setId(UUID.randomUUID());
            followRepository.save(Follow.builder()
                    .member(member)
                    .following(star)
                    .build());
        }
        List<Follow> follow = followRepository.findAll();

        // then
        assertThat(follow.size()).isGreaterThan(5);
    }

}