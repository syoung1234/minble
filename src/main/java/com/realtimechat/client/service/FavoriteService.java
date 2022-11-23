package com.realtimechat.client.service;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String, String> save(FavoriteRequestDto favoriteRequestDto) {
        String message = "insert";
        Map<String, String> result = new HashMap<>();

        Favorite favorite = favoriteRepository.findByMemberAndPost(favoriteRequestDto.getMember(), favoriteRequestDto.getPost());
        
        if (favorite != null) {
            favoriteRepository.delete(favorite);
            message = "delete";
        } else {
            favoriteRepository.save(favoriteRequestDto.toEntity());
        }

        // 해당 게시글 좋아요 수 
        Long favoriteCount = favoriteRepository.countByPost(favoriteRequestDto.getPost());

        result.put("message", message);
        result.put("favoriteCount", favoriteCount.toString());

        return result;
        
    }
    
}
