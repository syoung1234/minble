package com.realtimechat.client.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import com.realtimechat.client.domain.PostFile;
import com.realtimechat.client.repository.PostFileRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file/post")
public class PostFileController {

    private final PostFileRepository postFileRepository;

    @Value("${file.path}")
    private String filePath;

    // 파일 다운로드
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        PostFile file = postFileRepository.findByFilename(filename);
        UrlResource urlResource = new UrlResource("file:" + filePath + file.getFilePath());

        // 업로드 한 파일명이 한글인 경우
        String encodedFileName = UriUtils.encode(file.getFilename(), StandardCharsets.UTF_8);
        // 파일 다운로드 상자가 뜨도록 헤더 설정
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(urlResource);
    }
    
}
