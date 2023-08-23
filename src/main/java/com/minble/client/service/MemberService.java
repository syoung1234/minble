package com.minble.client.service;

import java.util.*;
import java.util.stream.Collectors;

import com.minble.client.config.security.JwtTokenProvider;
import com.minble.client.domain.Member;
import com.minble.client.domain.Role;
import com.minble.client.dto.request.SocialRegisterRequestDto;
import com.minble.client.dto.request.member.DuplicateRequestDto;
import com.minble.client.dto.request.member.LoginRequestDto;
import com.minble.client.dto.request.member.RegisterRequestDto;
import com.minble.client.dto.response.LoginResponseDto;
import com.minble.client.dto.response.MemberResponseDto;
import com.minble.client.exception.ErrorCode;
import com.minble.client.exception.MemberException;
import com.minble.client.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${default.profile.path}")
    private String defaultProfilePath;

    /**
     * 로그인
     * @param requestDto (email, password)
     * @return loginResponseDto, MemberException
     */
    public LoginResponseDto login(LoginRequestDto requestDto) {
        Member member = memberRepository.findByEmailAndSocial(requestDto.getEmail(), null)
                        .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND)); // 가입X

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) { // 비밀번호 틀림
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }

        LoginResponseDto loginResponseDto;
        if (!member.isEmailConfirmation()) { // 이메일 인증이 안 된 회원
            throw new MemberException(ErrorCode.UNAUTHORIZED_MEMBER);
        } else {
            String accessToken = jwtTokenProvider.createToken(member.getEmail(), member.getRole(), null);
            String refreshToken = jwtTokenProvider.createRefreshToken(member);
            loginResponseDto = new LoginResponseDto(accessToken, refreshToken, "success", member.getRole().toString(), member.getNickname());
        }

        return loginResponseDto;
    }

    /**
     * 회원가입
     * @param registerRequestDto (email, password, nickname)
     * @return member
     */
    @Transactional
    public Member register(RegisterRequestDto registerRequestDto) {
        Member member = Member.builder()
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .nickname(registerRequestDto.getNickname())
                .profilePath(defaultProfilePath)
                .role(Role.ROLE_MEMBER)
                .build();

        return memberRepository.save(member);
    }

    /**
     * 소셜 회원가입 및 로그인 (네이버, 카카오, 구글)
     * @param socialRegisterRequestDto (email, password, social)
     * @return LoginResponseDto
     */
    @Transactional
    public LoginResponseDto socialSave(SocialRegisterRequestDto socialRegisterRequestDto) {
        Member member = memberRepository.findByEmailAndSocial(socialRegisterRequestDto.getEmail(), socialRegisterRequestDto.getSocial())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        String accessToken = jwtTokenProvider.createToken(member.getNickname(), member.getRole(), member.getSocial());
        String refreshToken = jwtTokenProvider.createRefreshToken(member);

        return new LoginResponseDto(accessToken, refreshToken, "success", member.getRole().toString(), member.getNickname());
    }

    /**
     * 이메일 또는 닉네임 중복 검사
     * @param duplicateRequestDto (email, nickname)
     * @param type (email, nickname)
     * @return message, MemberException
     */
    public String duplicate(DuplicateRequestDto duplicateRequestDto, String type) {
        String message = "available";
        Optional<Member> member = Optional.of(new Member());

        if (type.equals("email")) {
            member = memberRepository.findByEmail(duplicateRequestDto.getEmail());

        } else if (type.equals("nickname")) {
            member = memberRepository.findByNickname(duplicateRequestDto.getNickname());
        }

        if (!member.isEmpty()) {
            throw new MemberException(ErrorCode.DUPLICATED_MEMBER);
        }
        return message;
    }

    /**
     * role type = 스타 리스트
     * @return List<MemberResponseDto>
     */
    public List<MemberResponseDto> starList() {
        List<Member> list = memberRepository.findByRoleOrRoleOrderByCreatedAtDesc(Role.ROLE_STAR, Role.ROLE_STAR_TEST);

        if (list.isEmpty()) {
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return list.stream().map(MemberResponseDto::new).collect(Collectors.toList());
    }
}
