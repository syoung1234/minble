package com.realtimechat.client.controller;

import java.io.File;
import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.dto.request.PostFileRequestDto;
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.dto.response.PostResponseDto;
import com.realtimechat.client.service.PostFileService;
import com.realtimechat.client.service.PostService;
import com.realtimechat.client.util.CreateFileName;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final PostFileService postFileService;

    @GetMapping
    public Map<String, Object> list(@AuthenticationPrincipal SecurityUser principal) {
        return postService.list(principal.getMember());
    }

    @PostMapping(value = "/create")
    public String create(@ModelAttribute PostRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {

        try {
            MultipartFile files = requestDto.getFiles();
            requestDto.setMember(principal.getMember());
            Post post = postService.save(requestDto);

            // 이미지 업로드 
            String originalFileName = files.getOriginalFilename();
            String filename = new CreateFileName(originalFileName).toString();
            Long fileSize = files.getSize();
            String fileType = files.getContentType();
            String savePath = System.getProperty("user.dir") + "/files";
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch(Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "/" + filename;
            files.transferTo(new File(filePath));

            PostFileRequestDto postFileRequestDto = new PostFileRequestDto();
            postFileRequestDto.setOriginalFileName(originalFileName);
            postFileRequestDto.setFilename(filename);
            postFileRequestDto.setFilePath(filePath);
            postFileRequestDto.setFileSize(fileSize);
            postFileRequestDto.setFileType(fileType);
            postFileRequestDto.setPost(post);
            
            postFileService.save(postFileRequestDto);


        } catch(Exception e) {
            e.printStackTrace();
        }
        

        return "success";
    }
    
    @PutMapping("{id}/update")
    public String update(@PathVariable Integer id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {
        requestDto.setMember(principal.getMember());
        return postService.update(id, requestDto);
    }

    @GetMapping("/{id}")
    public PostResponseDto get(@PathVariable Integer id) {
        return postService.find(id);
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, @AuthenticationPrincipal SecurityUser principal) {
        return postService.delete(id, principal.getMember());
    }

}
