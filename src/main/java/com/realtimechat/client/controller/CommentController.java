package com.realtimechat.client.controller;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.request.CommentRequestDto;
import com.realtimechat.client.service.CommentService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 추가
    @PostMapping
    public String save(@AuthenticationPrincipal SecurityUser principal, @RequestBody CommentRequestDto commentRequestDto) {
        commentRequestDto.setMember(principal.getMember());

        return commentService.save(commentRequestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, @AuthenticationPrincipal SecurityUser principal) {
        return commentService.delete(principal.getMember(), id);
    }
    
}
