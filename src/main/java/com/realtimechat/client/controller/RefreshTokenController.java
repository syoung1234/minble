package com.realtimechat.client.controller;

import java.util.HashMap;
import java.util.Map;

import com.realtimechat.client.service.RefreshTokenService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refresh-token")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping
    public ResponseEntity<Map<String, String>> invalidToken() {
        Map<String, String> body = new HashMap<>();
        body.put("message", "token");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PostMapping
    public ResponseEntity<String> getToken(@CookieValue("refreshToken") String refreshToken) {
        String body = refreshTokenService.getToken(refreshToken);

        return ResponseEntity.ok(body);
    }
    

}
