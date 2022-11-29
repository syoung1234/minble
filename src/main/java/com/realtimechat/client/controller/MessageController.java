package com.realtimechat.client.controller;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.response.MessageResponseDto;
import com.realtimechat.client.service.MessageService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{nickname}")
    public ResponseEntity<MessageResponseDto> get(@AuthenticationPrincipal SecurityUser principal, @PathVariable String nickname) {
        MessageResponseDto response = messageService.get(principal.getMember(), nickname);

        return ResponseEntity.ok(response);

    }
    
}
