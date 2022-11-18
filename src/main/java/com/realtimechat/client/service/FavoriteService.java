package com.realtimechat.client.service;

import com.realtimechat.client.domain.Favorite;
import com.realtimechat.client.dto.request.FavoriteRequestDto;
import com.realtimechat.client.repository.FavoriteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    // 좋아요 추가/취소
    @Transactional
    public String save(FavoriteRequestDto favoriteRequestDto) {
        String message = "insert";
        Favorite favorite = favoriteRepository.findByMemberAndPost(favoriteRequestDto.getMember(), favoriteRequestDto.getPost());
        if (favorite != null) {
            favoriteRepository.delete(favorite);
            message = "delete";
        } else {
            favoriteRepository.save(favoriteRequestDto.toEntity());
        }

        return message;
        
    }
    
}
