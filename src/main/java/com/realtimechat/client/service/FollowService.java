package com.realtimechat.client.service;

import com.realtimechat.client.domain.Follow;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Role;
import com.realtimechat.client.dto.request.FollowRequestDto;
import com.realtimechat.client.dto.response.FollowResponseDto;
import com.realtimechat.client.dto.response.MemberResponseDto;
import com.realtimechat.client.exception.ErrorCode;
import com.realtimechat.client.exception.FollowException;
import com.realtimechat.client.exception.MemberException;
import com.realtimechat.client.repository.FollowRepository;
import com.realtimechat.client.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;


    /**
     * 팔로우한 팔로잉 리스트
     * @param member
     * @return
     */
    public List<FollowResponseDto> followList(Member member) {
        List<Follow> followList = followRepository.findByMemberOrderByCreatedAtDesc(member);

        if (followList.isEmpty()) {
            throw new FollowException(ErrorCode.FOLLOW_NOT_FOUND);
        }

        return followList.stream()
                .map(FollowResponseDto::new)
                .collect(Collectors.toList());

    }


    /**
     * 팔로우 등록, 테스트 계정은 테스트끼리만 팔로우 가능
     * @param requestDto (스타의 nickname)
     * @param member 로그인한 유저
     */
    @Transactional
    public Follow save(FollowRequestDto requestDto, Member member) {
        Member following = memberRepository.findByNickname(requestDto.getNickname()).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        // TEST 끼리만 가능
        if (member.getRole().equals(Role.ROLE_SUBSCRIBER_TEST)) {
            if (!following.getRole().equals(Role.ROLE_STAR_TEST)) {
                throw new FollowException(ErrorCode.FOLLOW_BAD_REQUEST);
            }
        }

        Optional<Follow> follow = followRepository.findByFollowingAndMember(following, member);
        if (follow.isPresent()) {
            throw new FollowException(ErrorCode.DUPLICATED_FOLLOW);
        }

        return followRepository.save(requestDto.toEntity(member, following));
    }

    /**
     * 팔로우 삭제
     * @param nickname 스타의 nickname
     * @param member 로그인한 유저
     */
    @Transactional
    public void delete(String nickname, Member member) {
        Member followingMember = memberRepository.findByNickname(nickname).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        Follow follow = followRepository.findByFollowingAndMember(followingMember, member).orElseThrow(() -> new FollowException(ErrorCode.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);

    }
}
