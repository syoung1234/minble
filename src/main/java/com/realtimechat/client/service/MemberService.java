package com.realtimechat.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.response.FollowResponseDto;
import com.realtimechat.client.dto.response.MemberResponseDto;
import com.realtimechat.client.repository.FollowRepository;
import com.realtimechat.client.repository.MemberRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

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

            memberList = memberRepository.findByRoleAndMemberNotIn(Role.ROLE_STAR, followingList);
        }

        List<MemberResponseDto> response = memberList.stream()
            .map(m-> new MemberResponseDto(m.getNickname(), m.getProfilePath()))
            .collect(Collectors.toList());

        return response;
        
    }
    
}
