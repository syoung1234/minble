package com.realtimechat.client.service;

import java.util.*;
import java.util.stream.Collectors;

import com.realtimechat.client.config.security.JwtTokenProvider;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.SocialRegisterRequestDto;
import com.realtimechat.client.dto.request.member.DuplicateRequestDto;
import com.realtimechat.client.dto.request.member.LoginRequestDto;
import com.realtimechat.client.dto.request.member.RegisterRequestDto;
import com.realtimechat.client.dto.response.FollowResponseDto;
import com.realtimechat.client.dto.response.LoginResponseDto;
import com.realtimechat.client.dto.response.MemberResponseDto;
import com.realtimechat.client.exception.MemberErrorCode;
import com.realtimechat.client.exception.MemberException;
import com.realtimechat.client.repository.FollowRepository;
import com.realtimechat.client.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
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
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)); // 가입X

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) { // 비밀번호 틀림
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        LoginResponseDto loginResponseDto;
        if (!member.isEmailConfirmation()) { // 이메일 인증이 안 된 회원
            throw new MemberException(MemberErrorCode.UNAUTHORIZED_MEMBER);
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
     * 소셜 회원가입 (네이버, 카카오, 구글)
     * @param socialRegisterRequestDto (email, password, social)
     * @return LoginResponseDto
     */
    @Transactional
    public LoginResponseDto socialSave(SocialRegisterRequestDto socialRegisterRequestDto) {
        Member member = memberRepository.findByEmailAndSocial(socialRegisterRequestDto.getEmail(), socialRegisterRequestDto.getSocial()).orElse(null);

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
            throw new MemberException(MemberErrorCode.DUPLICATED_MEMBER);
        }
        return message;
    }

    public List<MemberResponseDto> followList(Member member) {
        List<Member> memberList;
        if (member == null) {
            // 로그인 하지 않은 유저
            memberList = memberRepository.findByRoleOrRoleOrderByCreatedAtDesc(Role.ROLE_STAR, Role.ROLE_STAR_TEST);
        } else {
            // 로그인 한 유저
            List<FollowResponseDto> followList = followRepository.findByMemberOrderByCreatedAtDesc(member);
            List<UUID> followingList = new ArrayList<>();
            for (FollowResponseDto follow : followList) {
                followingList.add(follow.getFollowing().getId());
            }

            if (followList.size() == 0) {
                memberList = memberRepository.findByRoleOrRoleOrderByCreatedAtDesc(Role.ROLE_STAR, Role.ROLE_STAR_TEST);
            } else {
                memberList = memberRepository.findByRoleOrRoleAndMemberNotIn(Role.ROLE_STAR, Role.ROLE_STAR_TEST, followingList);
            }
        }

        List<MemberResponseDto> response = memberList.stream()
            .map(m-> new MemberResponseDto(m.getNickname(), m.getProfilePath()))
            .collect(Collectors.toList());

        return response;
        
    }
    
}
