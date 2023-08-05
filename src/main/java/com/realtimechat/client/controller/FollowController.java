package com.realtimechat.client.controller;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.dto.request.FollowRequestDto;
import com.realtimechat.client.service.FollowService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    /**
     * 팔로우
     * @param requestDto (nickname)
     * @param principal (member)
     * @return HttpStatus OK
     */
    @PostMapping("/create")
    public ResponseEntity<String> save(@RequestBody FollowRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {
        followService.save(requestDto, principal.getMember());

        return ResponseEntity.ok("success");

    }

    /**
     * 팔로우 취소
     * @param nickname 스타 nickname
     * @param principal member
     * @return HttpStatus OK
     */
    @DeleteMapping("{nickname}/delete")
    public ResponseEntity<String> delete(@PathVariable String nickname, @AuthenticationPrincipal SecurityUser principal) {
        followService.delete(nickname, principal.getMember());

        return ResponseEntity.ok("success");
    }
    
}
