package com.realtimechat.client.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.realtimechat.client.config.security.JwtTokenProvider;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.RefreshToken;
import com.realtimechat.client.repository.RefreshTokenRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public String getToken(String refreshToken) {
        String result = null;
        Optional<RefreshToken> token = refreshTokenRepository.findById(refreshToken);

        if (token.isPresent()) {
            if (token.get().getExpirationDate().isAfter(LocalDateTime.now())) {// 유효기간이 지나지 않았을 때
                // 재발급
                Member member = token.get().getMember();
                result = jwtTokenProvider.createToken(member.getNickname(), member.getRole(), member.getSocial());
            }
        }
        
        return result;
    }
}
