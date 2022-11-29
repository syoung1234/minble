package com.realtimechat.client.controller;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.request.MessageRequestDto;
import com.realtimechat.client.dto.response.MessageResponseDto;
import com.realtimechat.client.service.MessageService;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/receive")
    @SendTo("/send")
    public void SocketHandler(MessageRequestDto messageRequestDto) {
        simpMessagingTemplate.convertAndSend("/send/" + messageRequestDto.getChannel(), messageRequestDto);
        //return socketDto;
    }

    @GetMapping("/api/message/{nickname}")
    public ResponseEntity<MessageResponseDto> get(@AuthenticationPrincipal SecurityUser principal, @PathVariable String nickname) {
        MessageResponseDto response = messageService.get(principal.getMember(), nickname);

        return ResponseEntity.ok(response);

    }
    
}
