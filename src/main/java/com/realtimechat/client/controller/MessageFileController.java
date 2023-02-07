package com.realtimechat.client.controller;

import com.realtimechat.client.service.MessageFileService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messagefile")
public class MessageFileController {

    private final MessageFileService messageFileService;

    // 메시지 파일 저장
    @PostMapping
    public ResponseEntity<String> save(@RequestParam("file") MultipartFile file) {
        String response = messageFileService.save(file);

        return ResponseEntity.ok(response);
    }
    
}
