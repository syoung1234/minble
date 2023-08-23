package com.minble.client.controller;

import com.minble.client.config.security.SecurityUser;
import com.minble.client.dto.request.FavoriteRequestDto;
import com.minble.client.dto.response.FavoriteResponseDto;
import com.minble.client.service.FavoriteService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    /**
     * 좋아요 저장/삭제
     * @param principal 로그인한 유저
     * @param favoriteRequestDto postId
     * @return FavoriteResponseDto (long count, boolean like)
     */
    @PostMapping
    public FavoriteResponseDto saveDelete(@AuthenticationPrincipal SecurityUser principal, @RequestBody FavoriteRequestDto favoriteRequestDto) {
        return favoriteService.saveDelete(favoriteRequestDto, principal.getMember());
    }
    
}
