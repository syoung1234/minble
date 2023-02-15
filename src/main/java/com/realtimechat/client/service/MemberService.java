package com.realtimechat.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.realtimechat.client.config.security.JwtTokenProvider;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.SocialRegisterRequestDto;
import com.realtimechat.client.dto.response.FollowResponseDto;
import com.realtimechat.client.dto.response.LoginResponseDto;
import com.realtimechat.client.dto.response.MemberResponseDto;
import com.realtimechat.client.repository.FollowRepository;
import com.realtimechat.client.repository.MemberRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 로그인
    public LoginResponseDto login(Map<String, String> user) {
        Member member = memberRepository.findByEmailAndSocial(user.get("email"), null)
                        .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }

        LoginResponseDto loginResponseDto;
        if (member.isEmailConfirmation() == false) { // 이메일 인증이 안 된 회원 
            loginResponseDto = new LoginResponseDto(null, "unconfirmed", null, null);
        } else {
            String accessToken = jwtTokenProvider.createToken(member.getNickname(), member.getRole(), null);
            loginResponseDto = new LoginResponseDto(accessToken, "success", member.getRole().toString(), member.getNickname());
        }

        return loginResponseDto;
    }

    // 소셜 회원가입
    public LoginResponseDto socialSave(SocialRegisterRequestDto socialRegisterRequestDto) {
        Member member = memberRepository.findByEmailAndSocial(socialRegisterRequestDto.getEmail(), socialRegisterRequestDto.getSocial()).orElse(null);

        String accessToken = jwtTokenProvider.createToken(member.getNickname(), member.getRole(), member.getSocial());
        LoginResponseDto loginResponseDto = new LoginResponseDto(accessToken, "success", member.getRole().toString(), member.getNickname());

        return loginResponseDto;
    }

    public List<MemberResponseDto> followList(Member member) {
        List<Member> memberList;
        if (member == null) {
            // 로그인 하지 않은 유저
            memberList = memberRepository.findByRole(Role.ROLE_STAR);
        } else {
            // 로그인 한 유저
            List<FollowResponseDto> followList = followRepository.findByMember(member);
            List<UUID> followingList = new ArrayList<>();
            for (FollowResponseDto follow : followList) {
                followingList.add(follow.getFollowing().getId());
            }

            if (followList.size() == 0) {
                memberList = memberRepository.findByRole(Role.ROLE_STAR);
            } else {
                memberList = memberRepository.findByRoleAndMemberNotIn(Role.ROLE_STAR, followingList);
            }
        }

        List<MemberResponseDto> response = memberList.stream()
            .map(m-> new MemberResponseDto(m.getNickname(), m.getProfilePath()))
            .collect(Collectors.toList());

        return response;
        
    }
    
}
