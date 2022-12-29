package com.realtimechat.client.controller;


import java.util.HashMap;
import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.request.PaymentRequestDto;
import com.realtimechat.client.dto.response.SubscriberResponseDto;
import com.realtimechat.client.service.SubscriberService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @Value("${imp.key}")
    private String imp_key;

    @Value("${imp.secret}")
    private String imp_secret;

    // test
    @PostMapping("/test")
    public ResponseEntity<String> iamport() {
        // 인증 토큰 발급 받기
        Map<String, String> req = new HashMap<>();
        req.put("imp_key", imp_key);
        req.put("imp_secret", imp_secret);

        String url = "https://api.iamport.kr/users/getToken";

        RequestEntity<Map<String, String>> requestEntity = RequestEntity
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(req);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        System.out.println(response);
        System.out.println(response.getBody());


        return response;
    }

    // 구독 취소
    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(@AuthenticationPrincipal SecurityUser principal, String nickname) {
        String response = subscriberService.cancel(principal.getMember(), nickname);

        return ResponseEntity.ok(response);
    }

}
