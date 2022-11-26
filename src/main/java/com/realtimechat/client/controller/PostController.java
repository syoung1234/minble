package com.realtimechat.client.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.dto.request.PostFileRequestDto;
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.dto.response.PostDetailResponseDto;
import com.realtimechat.client.service.PostFileService;
import com.realtimechat.client.service.PostService;
import com.realtimechat.client.util.CreateFileName;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final PostFileService postFileService;

    // 목록
    @GetMapping()
    public Map<String, Object> list(@AuthenticationPrincipal SecurityUser principal, 
    @RequestParam(value = "nickname", required=false) String nickname, @PageableDefault Pageable pageable) {
        return postService.list(principal.getMember(), nickname, pageable);
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
    public PostDetailResponseDto get(@AuthenticationPrincipal SecurityUser principal, @PathVariable Integer id,
                                    @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.find(principal.getMember(), id, pageable);
    }


    // 삭제
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, @AuthenticationPrincipal SecurityUser principal) {
        return postService.delete(id, principal.getMember());
    }

}
