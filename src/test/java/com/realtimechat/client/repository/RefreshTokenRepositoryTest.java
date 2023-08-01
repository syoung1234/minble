package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.RefreshToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class RefreshTokenRepositoryTest extends TestBase {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    public void cleanUp() {
        refreshTokenRepository.deleteAll();
    }

    @Test
    @Transactional
    void save() {
        // given
        String token = UUID.randomUUID().toString();
        Member member = memberRepository.save(createMember(10));
        LocalDateTime expirationDate = LocalDateTime.now().plusMonths(1);
        long expirationTime = Timestamp.valueOf(expirationDate).getTime();

        RefreshToken refreshToken = new RefreshToken(token, member, expirationTime);

        // when
        RefreshToken result = refreshTokenRepository.save(refreshToken);

        // then
        Assertions.assertThat(result.getId()).isEqualTo(token);
    }

}