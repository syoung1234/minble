package com.realtimechat.client.controller;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.service.SubscriberService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subscriber")
public class SubscriberController {

    private final SubscriberService subscriberService;

    // 구독 
    public ResponseEntity<String> save(@AuthenticationPrincipal SecurityUser principal, String nickname) {
        String response = subscriberService.save(principal.getMember(), nickname);

        return ResponseEntity.ok(response);
    }
    
}
