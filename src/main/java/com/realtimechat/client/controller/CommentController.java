package com.realtimechat.client.controller;

import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.request.CommentRequestDto;
import com.realtimechat.client.service.CommentService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<String> save(@AuthenticationPrincipal SecurityUser principal, @RequestBody CommentRequestDto commentRequestDto) {
        commentRequestDto.setMember(principal.getMember());
        String response = commentService.save(commentRequestDto);
        return ResponseEntity.ok(response);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Integer id, @AuthenticationPrincipal SecurityUser principal) {
        String response = commentService.delete(principal.getMember(), id);
        return ResponseEntity.ok(response);
    }
    
    // 답글 더보기
    @GetMapping("/{parentId}/children")
    public ResponseEntity<Map<String, Object>> getChildren(@PathVariable Integer parentId, @PageableDefault Pageable pageable) {
        Map<String, Object> response = commentService.getChildren(parentId, pageable);
        return ResponseEntity.ok(response);
    }
}
