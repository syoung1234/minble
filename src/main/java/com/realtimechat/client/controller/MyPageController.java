package com.realtimechat.client.controller;

import java.util.Map;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.request.MyPageRequestDto;
import com.realtimechat.client.dto.response.MyPageResponseDto;
import com.realtimechat.client.service.MyPageService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    
    // 닉네임, 프로필 조회 
    @GetMapping
    public ResponseEntity<MyPageResponseDto> get(@AuthenticationPrincipal SecurityUser principal) {
        if (principal != null) {
            MyPageResponseDto myPageResponseDto = new MyPageResponseDto();
            myPageResponseDto.setNickname(principal.getMember().getNickname());
            myPageResponseDto.setProfilePath(principal.getMember().getProfilePath());

            return ResponseEntity.ok(myPageResponseDto);
        } else {
            return ResponseEntity.ok(null);
        }
        
    }

    // 닉네임 변경
    @PostMapping("/nickname")
    public ResponseEntity<String> updateNickname(@RequestBody MyPageRequestDto myPageRequestDto, @AuthenticationPrincipal SecurityUser principal) {
        String response = myPageService.updateNickname(principal.getMember(), myPageRequestDto.getNickname());
        return ResponseEntity.ok(response);
    }

    // 프로필 변경
    @PostMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestParam("profile") MultipartFile profile, @AuthenticationPrincipal SecurityUser principal) {
        System.out.println(profile);
        String response = myPageService.updateProfile(principal.getMember(), profile);
        return ResponseEntity.ok(response);
    }

    // 비밀번호 변경
    @PostMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody MyPageRequestDto myPageRequestDto, @AuthenticationPrincipal SecurityUser principal) {
        System.out.println(myPageRequestDto);
        String response = myPageService.updatePassword(principal.getMember(), myPageRequestDto);
        return ResponseEntity.ok(response);
    }

    // 결제 내역
    @GetMapping("/payment")
    public ResponseEntity<Map<String, Object>> getPaymentList(@AuthenticationPrincipal SecurityUser principal,
    @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> response = myPageService.getPaymentList(principal.getMember(), pageable);
        return ResponseEntity.ok(response);
    }

    // 작성한 댓글+답글
    @GetMapping("/comment")
    public Map<String, Object> getCommentList(@AuthenticationPrincipal SecurityUser principal,
    @PageableDefault(sort="createdAt", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        return myPageService.getCommentList(principal.getMember(), pageable);
    }

}
