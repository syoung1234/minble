package com.realtimechat.client.controller;

import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.service.SubscriberService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subscriber")
public class SubscriberController {

    private final SubscriberService subscriberService;

    // 구독 
    @PostMapping
    public ResponseEntity<String> save(@AuthenticationPrincipal SecurityUser principal, @RequestBody Map<String, String> body) {
        String response = subscriberService.save(principal.getMember(), body.get("nickname"));

        return ResponseEntity.ok(response);
    }

    // 구독 취소
    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(@AuthenticationPrincipal SecurityUser principal, String nickname) {
        String response = subscriberService.cancel(principal.getMember(), nickname);

        return ResponseEntity.ok(response);
    }

}
