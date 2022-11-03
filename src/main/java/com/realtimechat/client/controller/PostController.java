package com.realtimechat.client.controller;

import com.realtimechat.client.config.security.JwtTokenProvider;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.dto.response.PostResponseDto;
import com.realtimechat.client.service.PostService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public String create(@RequestBody PostRequestDto requestDto, Authentication authentication) {
        System.out.println("----------------");
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        
        postService.save(requestDto);
        return requestDto.toString();
    }
    
    @PutMapping("/update/{id}")
    public Integer update(@PathVariable Integer id, @RequestBody PostRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @GetMapping("/{id}")
    public PostResponseDto get(@PathVariable Integer id) {
        return postService.find(id);
    }

}
