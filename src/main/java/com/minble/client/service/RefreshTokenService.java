package com.minble.client.service;

import java.util.Optional;

import com.minble.client.config.security.JwtTokenProvider;
import com.minble.client.domain.Member;
import com.minble.client.domain.RefreshToken;
import com.minble.client.exception.ErrorCode;
import com.minble.client.exception.MemberException;
import com.minble.client.exception.RefreshTokenException;
import com.minble.client.repository.MemberRepository;
import com.minble.client.repository.RefreshTokenRepository;

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
