package com.realtimechat.client.controller;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.request.ReplyRequestDto;
import com.realtimechat.client.service.ReplyService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // 생성
    @PostMapping()
    public ResponseEntity<String> save(@AuthenticationPrincipal SecurityUser principal, @RequestBody ReplyRequestDto replyRequestDto) {
        String response = replyService.save(principal.getMember(), replyRequestDto);
        return ResponseEntity.ok(response);
    }
    
    // 삭제 
    
}
