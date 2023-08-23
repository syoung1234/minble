package com.minble.client.service;

import com.minble.client.config.security.JwtTokenProvider;
import com.minble.client.domain.Member;
import com.minble.client.domain.RefreshToken;
import com.minble.client.exception.ErrorCode;
import com.minble.client.exception.RefreshTokenException;
import com.minble.client.repository.MemberRepository;
import com.minble.client.repository.RefreshTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {
    @InjectMocks
    private RefreshTokenService refreshTokenService;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("refresh token 만료")
    @Test
    void tokenNotFound() {
        // given
        String refreshToken = UUID.randomUUID().toString();
        doReturn(Optional.empty()).when(refreshTokenRepository).findById(anyString());

        // when
        RefreshTokenException refreshTokenException = assertThrows(RefreshTokenException.class, () -> refreshTokenService.getToken(refreshToken));

        // then
        assertThat(refreshTokenException.getErrorCode()).isEqualTo(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }

    @DisplayName("access token 발급")
    @Test
    void getToken() {
        // given
        String accessToken = "accessTokenTest";
        String refreshToken = UUID.randomUUID().toString();
        Member member = new Member();

        doReturn(Optional.of(new RefreshToken())).when(refreshTokenRepository).findById(anyString());
        doReturn(Optional.of(new Member())).when(memberRepository).findById(any());
        doReturn(accessToken).when(jwtTokenProvider).createToken(member.getEmail(), member.getRole(), member.getSocial());

        // when
        String result = refreshTokenService.getToken(refreshToken);

        // then
        assertThat(result).isEqualTo(accessToken);

    }

}