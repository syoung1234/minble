package com.realtimechat.client.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.realtimechat.client.config.security.JwtTokenProvider;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.RefreshToken;
import com.realtimechat.client.exception.ErrorCode;
import com.realtimechat.client.exception.MemberException;
import com.realtimechat.client.exception.RefreshTokenException;
import com.realtimechat.client.repository.MemberRepository;
import com.realtimechat.client.repository.RefreshTokenRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    /**
     * Refresh Token 있음 -> Access Token 발급
     * Refresh Token 없음 -> exception
     * @param refreshToken
     * @return access token
     */
    public String getToken(String refreshToken) {
        String result;
        Optional<RefreshToken> token = refreshTokenRepository.findById(refreshToken);

        if (token.isPresent()) {
            Member member = memberRepository.findById(token.get().getMemberId())
                    .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
            result = jwtTokenProvider.createToken(member.getEmail(), member.getRole(), member.getSocial());
        } else {
            throw new RefreshTokenException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }
        
        return result;
    }
}
