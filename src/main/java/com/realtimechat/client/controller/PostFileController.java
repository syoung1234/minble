package com.realtimechat.client.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import com.realtimechat.client.domain.PostFile;
import com.realtimechat.client.dto.response.PostFileResponseDto;
import com.realtimechat.client.repository.PostFileRepository;

import com.realtimechat.client.service.PostFileService;
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

    private final PostFileService postFileService;

    // 파일 다운로드
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        PostFileResponseDto response = postFileService.download(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, response.getContentDisposition()).body(response.getUrlResource());
    }
    
}
