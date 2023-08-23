package com.minble.client.service;

import com.minble.client.domain.Favorite;
import com.minble.client.domain.Member;
import com.minble.client.domain.Post;
import com.minble.client.dto.request.FavoriteRequestDto;
import com.minble.client.dto.response.FavoriteResponseDto;
import com.minble.client.exception.ErrorCode;
import com.minble.client.exception.PostException;
import com.minble.client.repository.FavoriteRepository;
import com.minble.client.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @InjectMocks
    private FavoriteService favoriteService;
    @Mock
    private FavoriteRepository favoriteRepository;
    @Mock
    private PostRepository postRepository;

    @DisplayName("게시글 없음")
    @Test
    void post_not_found() {
        // given
        Member member = new Member();
        FavoriteRequestDto favoriteRequestDto = new FavoriteRequestDto();
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        // when
        PostException postException = assertThrows(PostException.class, () -> favoriteService.saveDelete(favoriteRequestDto, member));

        // then
        assertThat(postException.getErrorCode()).isEqualTo(ErrorCode.POST_NOT_FOUND);

    }

    @DisplayName("좋아요 저장")
    @Test
    void save() {
        // given
        Member member = new Member();
        FavoriteRequestDto favoriteRequestDto = new FavoriteRequestDto();
        Post post = new Post();
        when(postRepository.findById(any())).thenReturn(Optional.of(post));
        when(favoriteRepository.findByMemberAndPost(any(Member.class), any(Post.class))).thenReturn(Optional.empty());

        // when
        FavoriteResponseDto result = favoriteService.saveDelete(favoriteRequestDto, member);

        // then
        assertThat(result).isNotNull();
        assertTrue(result.isLike());
    }

    @DisplayName("좋아요 삭제 (취소)")
    @Test
    void delete() {
        // given
        Member member = new Member();
        FavoriteRequestDto favoriteRequestDto = new FavoriteRequestDto();
        Post post = new Post();
        Favorite favorite = new Favorite();
        when(postRepository.findById(any())).thenReturn(Optional.of(post));
        when(favoriteRepository.findByMemberAndPost(any(Member.class), any(Post.class))).thenReturn(Optional.of(favorite));

        // when
        FavoriteResponseDto result = favoriteService.saveDelete(favoriteRequestDto, member);

        // then
        assertFalse(result.isLike());

    }
}