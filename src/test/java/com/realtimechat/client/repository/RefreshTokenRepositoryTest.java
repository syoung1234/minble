package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.RefreshToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class RefreshTokenRepositoryTest {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    void save() {
        String token = UUID.randomUUID().toString();
        Member member = new Member();
        LocalDateTime expiredDate = LocalDateTime.now().plusMonths(1);
        RefreshToken refreshToken = new RefreshToken(token, member, expiredDate);

        refreshTokenRepository.save(refreshToken);
    }

}