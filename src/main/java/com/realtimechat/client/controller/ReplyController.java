package com.realtimechat.client.controller;

import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.request.ReplyRequestDto;
import com.realtimechat.client.service.ReplyService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reply")
public class ReplyController {
    private final ReplyService replyService;

    // 목록
    @GetMapping("/{commentId}")
    public ResponseEntity<Map<String,Object>> list(@PathVariable Integer commentId, @PageableDefault Pageable pageable) {
        Map<String, Object> response = replyService.list(commentId, pageable);

        return ResponseEntity.ok(response);
    }

    // 생성
    @PostMapping()
    public ResponseEntity<String> save(@AuthenticationPrincipal SecurityUser principal, @RequestBody ReplyRequestDto replyRequestDto) {
        String response = replyService.save(principal.getMember(), replyRequestDto);
        return ResponseEntity.ok(response);
    }
    
    // 삭제 
    
}
