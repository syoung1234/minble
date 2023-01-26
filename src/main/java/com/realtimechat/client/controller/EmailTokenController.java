package com.realtimechat.client.controller;

import com.realtimechat.client.service.EmailTokenService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email/confirm")
public class EmailTokenController {
    private final EmailTokenService emailTokenService;

    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        String message = emailTokenService.confirmEmail(token);

        return ResponseEntity.ok(message);
    }
}
