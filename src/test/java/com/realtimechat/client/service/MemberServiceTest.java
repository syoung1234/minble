package com.realtimechat.client.service;

import com.realtimechat.client.config.security.JwtTokenProvider;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.SocialRegisterRequestDto;
import com.realtimechat.client.dto.request.member.DuplicateRequestDto;
import com.realtimechat.client.dto.request.member.LoginRequestDto;
import com.realtimechat.client.dto.request.member.RegisterRequestDto;
import com.realtimechat.client.dto.response.LoginResponseDto;
import com.realtimechat.client.exception.ErrorCode;
import com.realtimechat.client.exception.MemberException;
import com.realtimechat.client.repository.MemberRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private final String email = "test222@test.com";
    private final String nickname = "test222";
    private final String password = "1234";

    @DisplayName("이메일 중복 검사-존재O")
    @Test
    void duplicate_email_o() {
        // given
        Member member = new Member();
        doReturn(Optional.of(member)).when(memberRepository).findByEmail(email);

        // when
        DuplicateRequestDto duplicateRequestDto = new DuplicateRequestDto(email, null);
        MemberException memberException = assertThrows(MemberException.class, () -> memberService.duplicate(duplicateRequestDto, "email"));

        // then
        assertThat(memberException.getErrorCode()).isEqualTo(ErrorCode.DUPLICATED_MEMBER);
    }

    @DisplayName("이메일 중복 검사-존재X")
    @Test
    void duplicate_email_x() {
        // given
        doReturn(Optional.empty()).when(memberRepository).findByEmail(email);

        // when
        DuplicateRequestDto duplicateRequestDto = new DuplicateRequestDto(email, null);
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

        // when
        DuplicateRequestDto duplicateRequestDto = new DuplicateRequestDto(null, nickname);
        MemberException memberException = assertThrows(MemberException.class, () -> memberService.duplicate(duplicateRequestDto, "nickname"));

        // then
        assertThat(memberException.getErrorCode()).isEqualTo(ErrorCode.DUPLICATED_MEMBER);
    }

    @DisplayName("닉네임 중복 검사-존재X")
    @Test
    void duplicate_nickname_x() {
        // given
        doReturn(Optional.empty()).when(memberRepository).findByNickname(nickname);

        // when
        DuplicateRequestDto duplicateRequestDto = new DuplicateRequestDto(null, nickname);
        String result = memberService.duplicate(duplicateRequestDto, "nickname");

        // then
        assertThat(result).isEqualTo("available");
    }

    @DisplayName("회원가입")
    @Test
    void register() {
        // given
        doReturn(member()).when(memberRepository).save(any(Member.class));

        // when
        RegisterRequestDto registerRequestDto = new RegisterRequestDto(email, password, nickname);
        Member result = memberService.register(registerRequestDto);

        // then
        assertThat(result.getNickname()).isEqualTo(nickname);
        assertThat(result.getEmail()).isEqualTo(email);
    }

    @DisplayName("소셜 로그인")
    @Test
    void social_register() {
        // given
        Member member = new Member();
        doReturn(Optional.of(member)).when(memberRepository).findByEmailAndSocial(email, "naver");
        member.setNickname(nickname);
        member.setRole(Role.ROLE_MEMBER);
        member.setSocial("naver");

        // when
        SocialRegisterRequestDto socialRegisterRequestDto = new SocialRegisterRequestDto(email, nickname, "naver");
        LoginResponseDto result = memberService.socialSave(socialRegisterRequestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("success");
    }

    @DisplayName("가입하지 않은 멤버")
    @Test
    void not_found_member() {
        // given
        doReturn(Optional.empty()).when(memberRepository).findByEmailAndSocial(email, null);

        // when
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        MemberException memberException = assertThrows(MemberException.class, () -> memberService.login(loginRequestDto));

        // then
        assertThat(memberException.getErrorCode()).isEqualTo(ErrorCode.MEMBER_NOT_FOUND);
    }

    @DisplayName("비밀번호 틀렸을 때")
    @Test
    void fail_password() {
        // given
        Member member = new Member();
        doReturn(Optional.of(member)).when(memberRepository).findByEmailAndSocial(email, null);
        doReturn(false).when(passwordEncoder).matches(anyString(), nullable(String.class));

        // when
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        MemberException memberException = assertThrows(MemberException.class, () -> memberService.login(loginRequestDto));

        // then
        assertThat(memberException.getErrorCode()).isEqualTo(ErrorCode.MEMBER_NOT_FOUND);
    }

    @DisplayName("이메일 인증이 완료 되지 않음")
    @Test
    void email_confirm_x() {
        // given
        Member member = new Member();
        doReturn(Optional.of(member)).when(memberRepository).findByEmailAndSocial(email, null);
        doReturn(true).when(passwordEncoder).matches(anyString(), nullable(String.class));
        member.setEmailConfirmation(false);

        // when
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        MemberException memberException = assertThrows(MemberException.class, () -> memberService.login(loginRequestDto));

        // then
        assertThat(memberException.getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED_MEMBER);
    }

    @DisplayName("로그인 성공")
    @Test
    void login() {
        // given
        Member member = new Member();
        doReturn(Optional.of(member)).when(memberRepository).findByEmailAndSocial(email, null);
        doReturn(true).when(passwordEncoder).matches(anyString(), nullable(String.class));
        member.setEmailConfirmation(true);
        member.setRole(Role.ROLE_MEMBER);

        // when
        LoginRequestDto requestDto = new LoginRequestDto(email, password);
        LoginResponseDto result = memberService.login(requestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("success");
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