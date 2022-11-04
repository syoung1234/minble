package com.realtimechat.client.service;

import com.realtimechat.client.dto.request.FollowRequestDto;
import com.realtimechat.client.repository.FollowRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;

    public String create(FollowRequestDto requestDto) {
        followRepository.save(requestDto.toEntity());
        return "success";
    }
    
}
