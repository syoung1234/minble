package com.realtimechat.client.controller;

import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.request.CommentRequestDto;
import com.realtimechat.client.dto.response.CommentResponseDto;
import com.realtimechat.client.service.CommentService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 조회
     * @param postId 게시글 Id
     * @param pageable 페이지
     * @return ResponseEntity<Page<CommentResponseDto>>
     */
    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> find(@RequestParam Integer postId, @PageableDefault Pageable pageable) {
        Page<CommentResponseDto> response = commentService.find(postId, pageable);
        return ResponseEntity.ok(response);
    }


    /**
     * 댓글 저장
     * @param principal 댓글 작성자
     * @param commentRequestDto (Member member, Post post, String content, Integer postId, Integer parentId, Integer depth)
     * @return success
     */
    @PostMapping
    public ResponseEntity<String> save(@AuthenticationPrincipal SecurityUser principal, @RequestBody CommentRequestDto commentRequestDto) {
        String response = commentService.save(commentRequestDto, principal.getMember());
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
