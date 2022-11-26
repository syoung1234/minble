package com.realtimechat.client.controller;

import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Post;
import com.realtimechat.client.dto.request.PostRequestDto;
import com.realtimechat.client.dto.response.PostDetailResponseDto;
import com.realtimechat.client.service.PostService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    // 목록
    @GetMapping()
    public ResponseEntity<Map<String, Object>> list(@AuthenticationPrincipal SecurityUser principal, 
    @RequestParam(value = "nickname", required=false) String nickname, @PageableDefault Pageable pageable) {
        Map<String, Object> response = postService.list(principal.getMember(), nickname, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 생성 
    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@ModelAttribute PostRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {
        
        requestDto.setMember(principal.getMember());
        String response = postService.save(requestDto);

        return ResponseEntity.ok(response);
    }
    
    // 수정 
    @PutMapping("{id}/update")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {
        requestDto.setMember(principal.getMember());
        String response = postService.update(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponseDto> get(@AuthenticationPrincipal SecurityUser principal, @PathVariable Integer id,
                                    @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        PostDetailResponseDto response = postService.find(principal.getMember(), id, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 삭제
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Integer id, @AuthenticationPrincipal SecurityUser principal) {
        String response = postService.delete(id, principal.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    

}
