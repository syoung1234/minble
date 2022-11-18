package com.realtimechat.client.controller;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.dto.request.FavoriteRequestDto;
import com.realtimechat.client.repository.PostRepository;
import com.realtimechat.client.service.FavoriteService;

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
    private final PostRepository postRepository;

    // 좋아요 추가/취소
    @PostMapping
    public String save(@AuthenticationPrincipal SecurityUser principal, @RequestBody FavoriteRequestDto favoriteRequestDto) {
        Post post = postRepository.findById(favoriteRequestDto.getPostId()).get();

        favoriteRequestDto.setMember(principal.getMember());
        favoriteRequestDto.setPost(post);
        
        return favoriteService.save(favoriteRequestDto);
    }
    
}
