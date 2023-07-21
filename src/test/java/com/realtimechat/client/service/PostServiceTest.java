package com.realtimechat.client.service;

import com.realtimechat.client.domain.*;
import com.realtimechat.client.dto.response.PostResponseDto;
import com.realtimechat.client.exception.ErrorCode;
import com.realtimechat.client.exception.PostException;
import com.realtimechat.client.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Value("${default.profile.path}")
    private String defaultProfilePath;

    private String nickname = "star1";

    @DisplayName("팔로우한 게시글 리스트")
    @Test
    void list() {
        // given
        Member member = memberRepository.save(member(10));
        Pageable pageable = PageRequest.of(0, 10);
        List<PostResponseDto> list = Arrays.asList(
                new PostResponseDto("star1", "profilePath", 10,"게시글 내용!", LocalDateTime.now(),3, 4),
                new PostResponseDto(),
                new PostResponseDto()
        );
        Page<PostResponseDto> pages = new PageImpl<>(list, pageable, list.size());
        doReturn(pages).when(postRepository).findAllByPostAndFollowingAndCountCommentAndCountFavorite(member, nickname, pageable);

        // when
        Page<PostResponseDto> result = postService.list(member, nickname, pageable);

        // then
        assertThat(result.get()).isNotNull();
        assertThat(result.get().count()).isEqualTo(3);
        assertThat(result.get().findFirst().get().getCommentCount()).isEqualTo(3);
    }

    @DisplayName("게시글 상세 조회")
    @Test
    void find() {
        // given
        PostResponseDto postResponseDto = new PostResponseDto("star1", "profilePath", 10,"게시글 내용!", LocalDateTime.now(),20, 4);
        doReturn(postResponseDto).when(postRepository).findByPostAndCountCommentAndCountFavorite(10);

        // when
        PostResponseDto result = postService.find(10);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getCommentCount()).isEqualTo(20);
    }

    @DisplayName("조회한 게시글이 없을 때")
    @Test
    void not_found_post() {
        // given
        doReturn(null).when(postRepository).findByPostAndCountCommentAndCountFavorite(10);

        // when
        PostException postException = assertThrows(PostException.class, () -> postService.find(10));

        // then
        assertThat(postException.getErrorCode()).isEqualTo(ErrorCode.POST_NOT_FOUND);
    }

    private Member member(int i) {
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

}