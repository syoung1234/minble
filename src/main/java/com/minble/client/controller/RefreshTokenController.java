package com.minble.client.controller;

import com.minble.client.service.RefreshTokenService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refresh-token")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    /**
     * Access Token 재발급
     * @param refreshToken
     * @return Access Token
     */
    @PostMapping
    public ResponseEntity<String> getToken(@CookieValue("refreshToken") String refreshToken) {
        String body = refreshTokenService.getToken(refreshToken);

        return ResponseEntity.ok(body);
    }
    

}
