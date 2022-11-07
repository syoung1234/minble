package com.realtimechat.client.controller;

import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.dto.response.PostResponseDto;
import com.realtimechat.client.service.PostService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping
    public Map<String, Object> list(@AuthenticationPrincipal SecurityUser principal) {
        return postService.list(principal.getMember());
    }

    @PostMapping("/create")
    public String create(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {
        requestDto.setMember(principal.getMember());
        postService.save(requestDto);

        return requestDto.toString();
    }
    
    @PutMapping("{id}/update")
    public String update(@PathVariable Integer id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {
        requestDto.setMember(principal.getMember());
        return postService.update(id, requestDto);
    }

    @GetMapping("/{id}")
    public PostResponseDto get(@PathVariable Integer id) {
        return postService.find(id);
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, @AuthenticationPrincipal SecurityUser principal) {
        return postService.delete(id, principal.getMember());
    }

}
