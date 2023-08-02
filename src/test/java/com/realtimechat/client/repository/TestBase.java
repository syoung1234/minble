package com.realtimechat.client.repository;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class TestBase {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Value("${default.profile.path}")
    private String defaultProfilePath;

    protected Member createMember(int i) {
        return Member.builder()
                .email("test" + i + "@test.com")
                .password(passwordEncoder.encode("1234"))
                .nickname("test" + i)
                .profilePath(defaultProfilePath)
                .role(Role.ROLE_MEMBER)
                .social(null)
                .emailConfirmation(true)
                .build();
    }

    protected Member createStarMember(int i) {
        return Member.builder()
                .email("star" + i + "@test.com")
                .password(passwordEncoder.encode("1234"))
                .nickname("star" + i)
                .profilePath(defaultProfilePath)
                .role(Role.ROLE_STAR)
                .social(null)
                .emailConfirmation(true)
                .build();
    }
}