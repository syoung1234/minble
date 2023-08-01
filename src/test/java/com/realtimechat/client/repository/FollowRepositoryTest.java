package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FollowRepositoryTest extends TestBase {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Value("${default.profile.path}")
    private String defaultProfilePath;

    @DisplayName("팔로우 하기")
    @Test
    @Transactional
    void insert() {
        // given
        Member member = memberRepository.save(createMember(10));

        // when
        for (int i = 10; i <= 15; i++) {
            Member star = memberRepository.save(createStarMember(i));
            followRepository.save(Follow.builder()
                    .member(member)
                    .following(star)
                    .build());
        }
        List<Follow> follow = followRepository.findByMember(member);

        // then
        assertThat(follow.size()).isGreaterThan(3);
        assertThat(follow.get(0).getMember().getEmail()).isEqualTo(member.getEmail());

    }


}