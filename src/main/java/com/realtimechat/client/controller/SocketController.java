package com.realtimechat.client.controller;

import com.realtimechat.client.dto.SocketDto;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/receive")

    @SendTo("/send")

    public void SocketHandler(SocketDto socketDto) {
        simpMessagingTemplate.convertAndSend("/send/" + socketDto.getChannel(), socketDto);
        //return socketDto;
    }
    
}
