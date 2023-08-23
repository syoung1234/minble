package com.minble.client.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import com.minble.client.domain.MessageFile;
import com.minble.client.repository.MessageFileRepository;
import com.minble.client.service.MessageFileService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messagefile")
public class MessageFileController {

    private final MessageFileService messageFileService;
    private final MessageFileRepository messageFileRepository;

    @Value("${file.path}")
    private String filePath;

    // 메시지 파일 저장
    @PostMapping
    public ResponseEntity<String> save(@RequestParam("file") MultipartFile file) {
        String response = messageFileService.save(file);

        return ResponseEntity.ok(response);
    }

    // 파일 다운로드
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        MessageFile file = messageFileRepository.findByFilename(filename);
        UrlResource urlResource = new UrlResource("file:" + filePath + file.getFilePath());

        // 업로드 한 파일명이 한글인 경우
        String encodedFileName = UriUtils.encode(file.getOriginalFileName(), StandardCharsets.UTF_8);
        // 파일 다운로드 상자가 뜨도록 헤더 설정
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(urlResource);
    }
    
}
