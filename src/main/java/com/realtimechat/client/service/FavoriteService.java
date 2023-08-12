package com.realtimechat.client.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.realtimechat.client.domain.Favorite;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.dto.request.FavoriteRequestDto;
import com.realtimechat.client.dto.response.FavoriteResponseDto;
import com.realtimechat.client.exception.ErrorCode;
import com.realtimechat.client.exception.PostException;
import com.realtimechat.client.repository.FavoriteRepository;

import com.realtimechat.client.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final PostRepository postRepository;

    /**
     * 좋아요 저장/삭제
     * @param favoriteRequestDto (postId)
     * @return FavoriteResponseDto (long count, boolean like)
     */
    @Transactional
    public FavoriteResponseDto save(FavoriteRequestDto favoriteRequestDto, Member member) {
        Post post = postRepository.findById(favoriteRequestDto.getPostId()).orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));
        Optional<Favorite> favorite = favoriteRepository.findByMemberAndPost(member, post);

        boolean like = favorite.isEmpty();

        if (like) { // 저장
            favoriteRepository.save(favoriteRequestDto.toEntity(member, post));
        } else { // 삭제
            favoriteRepository.delete(favorite.get());
        }

        Long favoriteCount = favoriteRepository.countByPost(post);

        return new FavoriteResponseDto(favoriteCount, like);
        
    }

}
