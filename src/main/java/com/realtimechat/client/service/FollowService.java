package com.realtimechat.client.service;

import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.FollowRequestDto;
import com.realtimechat.client.repository.FollowRepository;
import com.realtimechat.client.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String create(FollowRequestDto requestDto) {
        // TEST 끼리만 가능
        if (requestDto.getMember().getRole().equals(Role.ROLE_SUBSCRIBER_TEST)) {
            if (!requestDto.getFollowing().getRole().equals(Role.ROLE_STAR_TEST)) {
                return null;
            }
        }
        
        followRepository.save(requestDto.toEntity());
        return "success";
    }
    
    @Transactional
    public String delete(String nickname, Member member) {
        String message = "fail";
        Member followingMember = memberRepository.findByNickname(nickname).orElse(null);
        // Follow follow = followRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("데이터가 없습니다."));
        Follow follow = followRepository.findByFollowingAndMember(followingMember, member);

        System.out.println(follow);

        if (follow.getMember().getId().equals(member.getId())) { // 작성자만 삭제 가능
            followRepository.delete(follow);
            message = "success";
        }

        return message;
    }
}
