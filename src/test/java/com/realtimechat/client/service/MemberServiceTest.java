package com.realtimechat.client.service;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.member.DuplicateRequestDto;
import com.realtimechat.client.dto.request.member.RegisterRequestDto;
import com.realtimechat.client.exception.MemberErrorCode;
import com.realtimechat.client.exception.MemberException;
import com.realtimechat.client.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private final String email = "test222@test.com";
    private final String nickname = "test222";
    private final String password = "1234";

    @DisplayName("이메일 중복 검사-존재O")
    @Test
    void duplicate_email_o() {
        // given
        Member member = new Member();
        doReturn(Optional.of(member)).when(memberRepository).findByEmail(email);
        DuplicateRequestDto duplicateRequestDto = new DuplicateRequestDto(email, null);

        // when
        MemberException memberException = assertThrows(MemberException.class, () -> memberService.duplicate(duplicateRequestDto, "email"));

        // then
        assertThat(memberException.getMemberErrorCode()).isEqualTo(MemberErrorCode.DUPLICATED_MEMBER);
    }

    @DisplayName("이메일 중복 검사-존재X")
    @Test
    void duplicate_email_x() {
        // given
        DuplicateRequestDto duplicateRequestDto = new DuplicateRequestDto(email, null);

        // when
        String result = memberService.duplicate(duplicateRequestDto, "email");

        // then
        assertThat(result).isEqualTo("available");
    }

    @DisplayName("닉네임 중복 검사-존재O")
    @Test
    void duplicate_nickname_o() {
        // given
        Member member = new Member();
        doReturn(Optional.of(member)).when(memberRepository).findByNickname(nickname);
        DuplicateRequestDto duplicateRequestDto = new DuplicateRequestDto(null, nickname);

        // when
        MemberException memberException = assertThrows(MemberException.class, () -> memberService.duplicate(duplicateRequestDto, "nickname"));

        // then
        assertThat(memberException.getMemberErrorCode()).isEqualTo(MemberErrorCode.DUPLICATED_MEMBER);
    }

    @DisplayName("닉네임 중복 검사-존재X")
    @Test
    void duplicate_nickname_x() {
        // given
        DuplicateRequestDto duplicateRequestDto = new DuplicateRequestDto(null, nickname);

        // when
        String result = memberService.duplicate(duplicateRequestDto, "nickname");

        // then
        assertThat(result).isEqualTo("available");
    }

    @DisplayName("회원가입")
    @Test
    void register() {
        // given
        doReturn(member()).when(memberRepository).save(any(Member.class));
        RegisterRequestDto registerRequestDto = new RegisterRequestDto(email, password, nickname);

        // when
        Member result = memberService.register(registerRequestDto);

        // then
        assertThat(result.getNickname()).isEqualTo(nickname);
        assertThat(result.getEmail()).isEqualTo(email);
    }

    private Member member() {
        return Member.builder()
                .email(email)
                .password("1234")
                .nickname(nickname)
                .profilePath("path")
                .role(Role.ROLE_MEMBER)
                .social(null)
                .emailConfirmation(false)
                .build();
    }
}