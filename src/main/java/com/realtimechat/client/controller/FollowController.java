package com.realtimechat.client.controller;

import com.realtimechat.client.config.security.SecurityUser;
import com.realtimechat.client.domain.Member;
import com.realtimechat.client.dto.request.FollowRequestDto;
import com.realtimechat.client.repository.MemberRepository;
import com.realtimechat.client.service.FollowService;

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
    private final MemberRepository memberRepository;

    @PostMapping("/create")
    public void create(@RequestBody FollowRequestDto requestDto, @AuthenticationPrincipal SecurityUser principal) {
        Member following = memberRepository.findByNickname(requestDto.getNickname());
        requestDto.setFollowing(following);
        requestDto.setMember(principal.getMember()); // 본인 
        followService.create(requestDto);

    }

    @DeleteMapping("{id}/delete")
    public String delete(@PathVariable Integer id, @AuthenticationPrincipal SecurityUser principal) {
        return followService.delete(id, principal.getMember());
    }
    
}
