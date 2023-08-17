package com.realtimechat.client.controller;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.dto.request.FollowRequestDto;
import com.realtimechat.client.dto.response.FollowResponseDto;
import com.realtimechat.client.dto.response.MemberResponseDto;
import com.realtimechat.client.service.FollowService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    /**
     * 팔로우 리스트 조회
     * @param principal 로그인한 유저
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<FollowResponseDto>>followList(@AuthenticationPrincipal SecurityUser principal) {
        List<FollowResponseDto> response = followService.followList(principal.getMember());
        return ResponseEntity.ok(response);
    }


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
