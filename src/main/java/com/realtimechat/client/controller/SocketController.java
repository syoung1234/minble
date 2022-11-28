package com.realtimechat.client.controller;

import com.realtimechat.client.dto.SocketDto;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @MessageMapping("/receive")

    @SendTo("/send")

    public SocketDto SocketHandler(SocketDto socketDto) {

        return socketDto;
    }
    
}
