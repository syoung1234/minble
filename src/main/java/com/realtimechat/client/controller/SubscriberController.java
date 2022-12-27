package com.realtimechat.client.controller;


import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.request.PaymentRequestDto;
import com.realtimechat.client.dto.response.SubscriberResponseDto;
import com.realtimechat.client.service.SubscriberService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subscriber")
public class SubscriberController {

    private final SubscriberService subscriberService;

    // 페이지 조회
    @GetMapping
    public ResponseEntity<SubscriberResponseDto> get(@AuthenticationPrincipal SecurityUser principal, @RequestParam(value = "name") String name) {
        SubscriberResponseDto response = subscriberService.get(principal.getMember(), name);
        return ResponseEntity.ok(response);
    }

    // 구독 
    @PostMapping
    public ResponseEntity<String> save(@AuthenticationPrincipal SecurityUser principal, @RequestBody PaymentRequestDto body) {
        String response = subscriberService.save(principal.getMember(), body);

        return ResponseEntity.ok(response);
    }

    // 구독 취소
    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(@AuthenticationPrincipal SecurityUser principal, String nickname) {
        String response = subscriberService.cancel(principal.getMember(), nickname);

        return ResponseEntity.ok(response);
    }

}
