package com.minble.client.controller;

import com.minble.client.config.security.SecurityUser;
import com.minble.client.domain.Message;
import com.minble.client.dto.request.MessageRequestDto;
import com.minble.client.dto.response.MessageDetailResponseDto;
import com.minble.client.dto.response.MessageResponseDto;
import com.minble.client.service.MessageService;

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
        Message message =  messageService.save(messageRequestDto);
        MessageDetailResponseDto messageDetailResponse = new MessageDetailResponseDto(message);
        simpMessagingTemplate.convertAndSend("/send/" + messageRequestDto.getChannel(), messageDetailResponse);
        //return socketDto;
    }

    @GetMapping("/api/message/{nickname}")
    public ResponseEntity<MessageResponseDto> get(@AuthenticationPrincipal SecurityUser principal, @PathVariable String nickname) {
        // 권한 확인
        MessageResponseDto response = messageService.get(principal.getMember(), nickname);

        return ResponseEntity.ok(response);

    }
    
}
