package com.realtimechat.client.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.domain.PostFile;
import com.realtimechat.client.dto.request.PostFileRequestDto;
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.dto.response.PostDetailResponseDto;
import com.realtimechat.client.repository.PostFileRepository;
import com.realtimechat.client.service.PostFileService;
import com.realtimechat.client.service.PostService;
import com.realtimechat.client.util.CreateFileName;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final PostFileService postFileService;
    private final PostFileRepository postFileRepository;

    // 목록
    @GetMapping()
    public Map<String, Object> list(@AuthenticationPrincipal SecurityUser principal ,@RequestParam(value = "nickname", required=false) String nickname) {
        return postService.list(principal.getMember(), nickname);
    }

    // 생성 
    @PostMapping(value = "/create")
    public String create(@ModelAttribute PostRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {

        try {
            List<MultipartFile> files = requestDto.getFiles();
            requestDto.setMember(principal.getMember());
            Post post = postService.save(requestDto);

            if (files != null) {
                // 이미지 업로드 
                for (MultipartFile file : files) {
                    String originalFileName = file.getOriginalFilename();
                    String filename = new CreateFileName(originalFileName).toString();
                    Long fileSize = file.getSize();
                    String fileType = file.getContentType();
                    String savePath = System.getProperty("user.dir") + "/files/post";
                    if (!new File(savePath).exists()) {
                        try {
                            new File(savePath).mkdir();
                        } catch(Exception e) {
                            e.getStackTrace();
                        }
                    }
                    String filePath = savePath + "/" + filename;
                    file.transferTo(new File(filePath));
    
                    PostFileRequestDto postFileRequestDto = new PostFileRequestDto();
                    postFileRequestDto.setOriginalFileName(originalFileName);
                    postFileRequestDto.setFilename(filename);
                    postFileRequestDto.setFilePath(filePath);
                    postFileRequestDto.setFileSize(fileSize);
                    postFileRequestDto.setFileType(fileType);
                    postFileRequestDto.setPost(post);
                    
                    postFileService.save(postFileRequestDto);
                }
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
        

        return "success";
    }
    
    // 수정 
    @PutMapping("{id}/update")
    public String update(@PathVariable Integer id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {
        requestDto.setMember(principal.getMember());
        return postService.update(id, requestDto);
    }

    // 조회
    @GetMapping("/{id}")
    public PostDetailResponseDto get(@AuthenticationPrincipal SecurityUser principal, @PathVariable Integer id) {
        return postService.find(principal.getMember(), id);
    }

    // 파일 다운로드
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        PostFile file = postFileRepository.findByFilename(filename);
        UrlResource urlResource = new UrlResource("file:" + file.getFilePath());

        // 업로드 한 파일명이 한글인 경우
        String encodedFileName = UriUtils.encode(file.getFilename(), StandardCharsets.UTF_8);
        // 파일 다운로드 상자가 뜨도록 헤더 설정
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(urlResource);
    }
    

    // 삭제
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, @AuthenticationPrincipal SecurityUser principal) {
        return postService.delete(id, principal.getMember());
    }

}
