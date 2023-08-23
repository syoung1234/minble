package com.minble.client.service;

import com.minble.client.domain.Follow;
import com.minble.client.domain.Member;
import com.minble.client.domain.Role;
import com.minble.client.dto.request.FollowRequestDto;
import com.minble.client.dto.response.FollowResponseDto;
import com.minble.client.exception.ErrorCode;
import com.minble.client.exception.FollowException;
import com.minble.client.repository.FollowRepository;
import com.minble.client.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @InjectMocks
    private FollowService followService;
    @Mock
    private FollowRepository followRepository;
    @Mock
    private MemberRepository memberRepository;


    @DisplayName("팔로우 리스트 없음")
    @Test
    void followList_empty() {
        // given
        Member member = new Member();
        when(followRepository.findByMemberOrderByCreatedAtDesc(member)).thenReturn(Collections.emptyList());

        // when
        FollowException followException = assertThrows(FollowException.class, () -> followService.followList(member));

        // then
        assertThat(followException.getErrorCode()).isEqualTo(ErrorCode.FOLLOW_NOT_FOUND);
    }


    @DisplayName("멤버가 팔로우한 스타 리스트")
    @Test
    void followList() {
        // given
        Member member = new Member();
        member.setNickname("ttt");

        Member star = new Member();
        star.setNickname("star");

        Follow follow = new Follow();
        follow.setMember(member);
        follow.setFollowing(star);

        List<Follow> list = Arrays.asList(
                follow,
                follow
        );
        when(followRepository.findByMemberOrderByCreatedAtDesc(member)).thenReturn(list);

        // when
        List<FollowResponseDto> result = followService.followList(member);

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @DisplayName("팔로우 저장 실패")
    @Test
    void save_fail() {
        // given
        Member member = new Member();
        member.setRole(Role.ROLE_SUBSCRIBER_TEST);
        doReturn(Optional.of(member)).when(memberRepository).findByNickname(any());
        FollowRequestDto followRequestDto = new FollowRequestDto();

        // when
        FollowException followException = assertThrows(FollowException.class,
                () -> followService.save(followRequestDto, member));

        // then
        assertThat(followException.getErrorCode()).isEqualTo(ErrorCode.FOLLOW_BAD_REQUEST);

    }

    @DisplayName("팔로우 저장")
    @Test
    void save() {
        // given
        Member member = new Member();
        member.setRole(Role.ROLE_MEMBER);

        Member following = new Member();
        String nickname = "star";
        following.setNickname(nickname);

        FollowRequestDto followRequestDto = new FollowRequestDto(nickname);
        Follow follow = Follow.builder()
                .member(member)
                .following(following)
                .build();

        doReturn(Optional.of(member)).when(memberRepository).findByNickname(any());
        doReturn(follow).when(followRepository).save(any());

        // when
        Follow result = followService.save(followRequestDto, member);

        // then
        assertThat(result.getFollowing().getNickname()).isEqualTo(nickname);

    }

    @DisplayName("팔로우 데이터 없음")
    @Test
    void follow_not_found() {
        // given
        Member member = new Member();
        doReturn(Optional.of(member)).when(memberRepository).findByNickname(anyString());
        doReturn(Optional.empty()).when(followRepository).findByFollowingAndMember(any(Member.class), any(Member.class));

        // when
        FollowException followException = assertThrows(FollowException.class, () -> followService.delete(anyString(), new Member()));

        // then
        assertThat(followException.getErrorCode()).isEqualTo(ErrorCode.FOLLOW_NOT_FOUND);
    }

    @DisplayName("팔로우 삭제")
    @Test
    void delete_fail() {
        // given
        Member member = new Member();
        Follow follow = new Follow();
        doReturn(Optional.of(member)).when(memberRepository).findByNickname(anyString());
        doReturn(Optional.of(follow)).when(followRepository).findByFollowingAndMember(any(Member.class), any(Member.class));

        // when
        followService.delete(anyString(), member);

        // then
        verify(followRepository, times(1)).delete(follow);
    }

}