package com.minble.client.controller;

import java.net.MalformedURLException;

import com.minble.client.dto.response.PostFileDownloadResponseDto;

import com.minble.client.service.PostFileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file/post")
public class PostFileController {

    private final PostFileService postFileService;

    // 파일 다운로드
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        PostFileDownloadResponseDto response = postFileService.download(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, response.getContentDisposition()).body(response.getUrlResource());
    }
    
}
